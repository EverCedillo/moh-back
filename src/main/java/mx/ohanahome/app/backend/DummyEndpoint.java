package mx.ohanahome.app.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

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

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Dummy.class);
    }



    @ApiMethod(name = "getDummy")
    public Dummy getDummy(@Named("id")long id){
        logger.info("Getting Dummy with ID: " + id);

        String data="none";

        Connection connection=null;
        try {
            Class.forName("com.mysql.jdbc.GoogleDriver");
            connection = DriverManager.getConnection(
                    "jdbc:google:mysql://moh-333:test/test_gae",
                    "gae",
                    "gae_psw"
            );

            String query = "SELECT * FROM T01_TEST WHERE id =" +id;
            ResultSet set=connection.createStatement().executeQuery(query);
            String insert = "INSERT INTO T01_TEST VALUES(null,'"+id + " holi')";
            connection.createStatement().executeUpdate(insert);
            set.next();
            data+=" "+set.getString("name" );
        } catch (SQLException e) {
            e.printStackTrace();
            data = e.toString();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
            data = e.toString();
        }

        return new Dummy(data);
    }
}