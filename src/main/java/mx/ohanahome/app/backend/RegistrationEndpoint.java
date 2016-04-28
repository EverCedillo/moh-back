/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Backend with Google Cloud Messaging" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/GcmEndpoints
*/

package mx.ohanahome.app.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;

import java.util.List;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import mx.ohanahome.app.backend.entity.user.RegistrationRecord;
import mx.ohanahome.app.backend.util.DbConnection;


/**
 * A registration endpoint class we are exposing for a device's GCM registration id on the backend
 *
 * For more information, see
 * https://developers.google.com/appengine/docs/java/endpoints/
 *
 * NOTE: This endpoint does not use any form of authorization or
 * authentication! If this app is deployed, anyone can access this endpoint! If
 * you'd like to add authentication, take a look at the documentation.
 */
@Api(
  name = "registration",
  version = "v1",
  namespace = @ApiNamespace(
    ownerDomain = "backend.app.ohanahome.mx",
    ownerName = "backend.app.ohanahome.mx",
    packagePath=""
  )
)
public class RegistrationEndpoint {

    private static final Logger log = Logger.getLogger(RegistrationEndpoint.class.getName());

    /**
     * Register a device to the backend
     *
     * @param regId The Google Cloud Messaging registration Id to add
     */
    @ApiMethod(name = "register")
    public void registerDevice(@Named("regId") String regId) {
        DbConnection connection = new DbConnection();
        EntityManager manager = connection.getEntityManagerFactory("test_gae").createEntityManager();

        TypedQuery<RegistrationRecord> query = manager.createQuery("select t from RegistrationRecord t where regId='" + regId + "'", RegistrationRecord.class);

        List<RegistrationRecord> records = query.getResultList();
        RegistrationRecord record = records.isEmpty()?null:records.get(0);
        //RegistrationRecord record = manager.(RegistrationRecord.class,regId);
        if(record != null) {
            log.info("Device " + regId + " already registered, skipping register");
            return;
        }
        record = new RegistrationRecord();
        record.setRegId(regId);
        //todo save entity record

        manager.getTransaction().begin();
        manager.persist(record);
        manager.getTransaction().commit();
        manager.close();
    }

    /**
     * Unregister a device from the backend
     *
     * @param regId The Google Cloud Messaging registration Id to remove
     */
    @ApiMethod(name = "unregister")
    public void unregisterDevice(@Named("regId") String regId) {
        DbConnection connection = new DbConnection();
        EntityManager manager = connection.getEntityManagerFactory("test_gae").createEntityManager();

        TypedQuery<RegistrationRecord> query = manager.createQuery("select t from RegistrationRecord t where regId='"+regId+"'",RegistrationRecord.class);

        //RegistrationRecord record = query.getResultList().get(0); //manager.find(RegistrationRecord.class,regId);
        List<RegistrationRecord> records = query.getResultList();
        RegistrationRecord record = records.isEmpty()?null:records.get(0);
        if(record == null) {
            log.info("Device " + regId + " not registered, skipping unregister");
            manager.close();
            return;
        }
        //todo delete entity record
        manager.remove(record);
        manager.close();
    }

    /**
     * Return a collection of registered devices
     *
     * @param count The number of devices to list
     * @return a list of Google Cloud Messaging registration Ids
     */
    @ApiMethod(name = "listDevices")
    public CollectionResponse<RegistrationRecord> listDevices(@Named("count") int count) {
        DbConnection connection = new DbConnection();
        EntityManager manager = connection.getEntityManagerFactory("test_gae").createEntityManager();

        TypedQuery<RegistrationRecord> query = manager.createQuery("select t FROM RegistrationRecord t",RegistrationRecord.class);

        List<RegistrationRecord> records = query.getResultList(); //new ArrayList<>();//todo ofy().load().type(RegistrationRecord.class).limit(count).list();
        manager.close();
        return CollectionResponse.<RegistrationRecord>builder().setItems(records).build();
    }


}
