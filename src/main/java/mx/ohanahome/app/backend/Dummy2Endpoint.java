package mx.ohanahome.app.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import org.datanucleus.util.Base64;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.inject.Named;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;

import mx.ohanahome.app.backend.model.Dummy;
import mx.ohanahome.app.backend.model.Dummy2;
import mx.ohanahome.app.backend.util.DbConnection;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "dummy2Api",
        version = "v1",
        resource = "dummy2",
        namespace = @ApiNamespace(
                ownerDomain = "model.backend.app.ohanahome.mx",
                ownerName = "model.backend.app.ohanahome.mx",
                packagePath = ""
        )
)
public class Dummy2Endpoint {

    private static final Logger logger = Logger.getLogger(Dummy2Endpoint.class.getName());

    /**
     * This method gets the <code>Dummy2</code> object associated with the specified <code>id</code>.
     *
     * @param id The id of the object to be returned.
     * @return The <code>Dummy2</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getDummy2")
    public Dummy2 getDummy2(@Named("id") Long id) {
        // TODO: Implement this function
        DbConnection connection = new DbConnection();
        Dummy2 dummy2;
        EntityManager manager=connection.getEntityManagerFactory("test_gae").createEntityManager();
        dummy2=manager.find(Dummy2.class,id);


        return dummy2;
    }

    /**
     * This inserts a new <code>Dummy2</code> object.
     *
     * @param dummy2 The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertDummy2")
    public Dummy2 insertDummy2(Dummy2 dummy2) {
        // TODO: Implement this function
        logger.info("Calling insertDummy2 method");
        return dummy2;
    }

    @ApiMethod(name = "sendEmail",path = "dummy")
    public void sendEmail(@Named("to")String to,@Named("subject")String subject, @Named("iid")String id){
        Properties properties = new Properties();
        Session session = Session.getDefaultInstance(properties,null);

        String link = "http://www.ohanahome.mx/invite/?iid=";
        StringBuilder builder = new StringBuilder(id);
        link+=builder.reverse().toString();


        Message message = new MimeMessage(session);
        try {


            message.setFrom(new InternetAddress("ever@ohanahome.mx", "Holi"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            ((MimeMessage)message).setText("HOli crayoli <b>Negritas</b> <a href='"+link+"'>"+link+" </a>", "utf-8", "html");
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}