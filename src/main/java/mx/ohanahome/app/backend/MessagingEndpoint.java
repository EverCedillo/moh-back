/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Backend with Google Cloud Messaging" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/GcmEndpoints
*/

package mx.ohanahome.app.backend;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiNamespace;

import org.datanucleus.api.jpa.NucleusJPAHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.jdo.JDOHelper;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import mx.ohanahome.app.backend.entity.user.RegistrationRecord;
import mx.ohanahome.app.backend.model.NotificationPackage;
import mx.ohanahome.app.backend.util.EMFUser;


/**
 * An endpoint to send messages to devices registered with the backend
 *
 * For more information, see
 * https://developers.google.com/appengine/docs/java/endpoints/
 *
 * NOTE: This endpoint does not use any form of authorization or
 * authentication! If this app is deployed, anyone can access this endpoint! If
 * you'd like to add authentication, take a look at the documentation.
 */
@Api(
  name = "messaging",
  version = "v1",
  namespace = @ApiNamespace(
    ownerDomain = "backend.app.ohanahome.mx",
    ownerName = "backend.app.ohanahome.mx",
    packagePath=""
  )
)
public class MessagingEndpoint {
    private static final Logger log = Logger.getLogger(MessagingEndpoint.class.getName());

    /** Api Keys can be obtained from the google cloud console */
    private static final String API_KEY = System.getProperty("gcm.api.key");

    /**
     *
     *
     * @param message The message to send
     */
    public void sendMessage(@Named("message") String message, NotificationPackage notificationPackage, @Named("topic") String topic) throws IOException {
        if(message == null || message.trim().length() == 0) {
            log.warning("Not sending message because it is empty");
            return;
        }
        // crop longer messages
        if (message.length() > 1000) {
            message = message.substring(0, 1000) + "[...]";
        }
        Sender sender = new Sender(API_KEY);

        RegistrationRecord record = notificationPackage.getRegistrationRecord();
        HashMap<String,String> extras = notificationPackage.getExtras();
        Message.Builder builder =new Message.Builder().setData(extras);

        Message msg = builder.addData("message", message).addData("topic",topic).build();

        EntityManager entityManager;
        entityManager = NucleusJPAHelper.getEntityManager(record);
        boolean flag=entityManager==null;
        boolean flag2=true;
        if (flag)
            entityManager= EMFUser.get().createEntityManager();

        try {


            //here load into list the RegistrationRecord

            Result result = sender.send(msg, record.getToken(), 5);
            if (result.getMessageId() != null) {
                log.info("Message sent to " + record.getToken());
                String canonicalRegId = result.getCanonicalRegistrationId();
                if (canonicalRegId != null) {
                    // if the regId changed, we have to update the datastore
                    log.info("Registration Id changed for " + record.getToken() + " updating to " + canonicalRegId);
                    record.setToken(canonicalRegId);
                    //Here save object record
                    flag2=entityManager.getTransaction().isActive();

                    if(!flag2)
                        entityManager.getTransaction().begin();
                    entityManager.persist(record);
                    if (!flag2)
                        entityManager.getTransaction().commit();
                }
            } else {
                String error = result.getErrorCodeName();
                if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
                    log.warning("Registration Id " + record.getToken() + " no longer registered with GCM, removing from datastore");
                    // if the device is no longer registered with Gcm, remove it from the datastore
                    //Here delete object record

                    flag2 = entityManager.getTransaction().isActive();

                    if(!flag2)
                        entityManager.getTransaction().begin();
                    entityManager.remove(record);
                    if (!flag2)
                        entityManager.getTransaction().commit();
                } else {
                    log.warning("Error when sending message : " + error);
                }
            }

        }finally {
            if(!flag2)
                if(entityManager.getTransaction().isActive())
                    entityManager.getTransaction().rollback();
            if (flag)
                entityManager.close();
        }

    }
}
