package mx.ohanahome.app.backend.util;



import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static mx.ohanahome.app.backend.util.Constants.DB.*;
public class DbConnection {


    public EntityManagerFactory getEntityManagerFactory(String database) {

        Map<String, String> properties = new HashMap<>();
        properties.put("javax.persistence.jdbc.driver",
                DB_DRIVER);
        properties.put("javax.persistence.jdbc.url",
                DB_PROJECT+database);
        properties.put("javax.persistence.jdbc.user",
                DB_USER);
        properties.put("javax.persistence.jdbc.password",
                DB_PASSWORD);

        return Persistence.createEntityManagerFactory(
                "mx.ohanahome.app.backend", properties);


    }


}
