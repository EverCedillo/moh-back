package mx.ohanahome.app.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.googlecode.objectify.ObjectifyService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.inject.Named;

import mx.ohanahome.app.backend.model.Dummy;
import mx.ohanahome.app.backend.util.DbConection;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p/>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "dummyApi",
        version = "v1",
        resource = "dummy",
        namespace = @ApiNamespace(
                ownerDomain = "backend.app.ohanahome.mx",
                ownerName = "backend.app.ohanahome.mx",
                packagePath = ""
        )
)
public class DummyEndpoint {

    private static final Logger logger = Logger.getLogger(DummyEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;


    @ApiMethod(name = "getDummy")
    public Dummy getDummy(@Named("id")long id){
        logger.info("Getting Dummy with ID: " + id);

        String data="none";

        try {
            if(DbConection.startConnection("test_gae")){
                String[] columns ={"name"};
                ResultSet set=DbConection.select("T01_TEST", columns, "id=" + id);
                set.next();
                data+=set.getString("name");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            data = e.toString();
        }
        return new Dummy(data);
    }
}