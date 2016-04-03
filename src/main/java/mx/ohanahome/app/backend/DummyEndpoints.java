package mx.ohanahome.app.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ever on 31/03/16.
 */

@Api(
        name = "dummyApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.app.ohanahome.mx",
                ownerName = "backend.app.ohanahome.mx",
                packagePath=""
        )
)
public class DummyEndpoints {

    @ApiMethod (name = "readHoli")
    public Dummy sayDummyHoli(@Named(value = "name") String name){

        Connection connection;
        String data = "none";


        try {
            Class.forName("com.mysql.jdbc.GoogleDriver");
            connection = DriverManager.getConnection(
                    "jdbc:google:mysql://moh-333:test/test_gae",
                    "gae",
                    "gae_psw"
            );



            String query = "SELECT * FROM T01_TEST WHERE id = 1";
            ResultSet set=connection.createStatement().executeQuery(query);
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

        return new Dummy(name + " "+ data);
    }
}
