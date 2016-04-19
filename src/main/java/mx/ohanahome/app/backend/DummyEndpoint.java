package mx.ohanahome.app.backend;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.google.appengine.api.oauth.OAuthRequestException;

import javax.inject.Named;
import javax.persistence.EntityManager;

import mx.ohanahome.app.backend.entity.Dummy;
import mx.ohanahome.app.backend.util.DbConnection;


@Api(
        name = "dummyApi",
        version = "v1",
        resource = "dummy",
        clientIds = {"616148099676-jeb8gophf81m3rhqsob4kc1gtku66hb8.apps.googleusercontent.com"},
        namespace = @ApiNamespace(
                ownerDomain = "backend.app.ohanahome.mx",
                ownerName = "backend.app.ohanahome.mx",
                packagePath = ""
        )

)
public class DummyEndpoint {


    private static final int DEFAULT_LIST_LIMIT = 20;



    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    @ApiMethod(name = "getDummy")
    public Dummy getDummy(@Named("id")long id) throws OAuthRequestException{

        DbConnection connection = new DbConnection();

        Dummy dummy;
        EntityManager entityManager= connection.getEntityManagerFactory("test_gae").createEntityManager();
        dummy=entityManager.find(Dummy.class, id);


        return dummy;
    }

    @ApiMethod(name = "insertDummy")
    public Dummy insertDummy(Dummy dummy){
        DbConnection connection = new DbConnection();
        EntityManager manager = connection.getEntityManagerFactory("test_gae").createEntityManager();
        manager.getTransaction().begin();
        manager.persist(dummy);
        manager.getTransaction().commit();
        manager.close();

        return dummy;
    }
}