package mx.ohanahome.app.backend.util;



import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class DbConnection {
    public static final String USER = "gae";
    public static final String PASSWORD = "gae_psw";
    public static final String DRIVER = "com.mysql.jdbc.GoogleDriver";
    public static final String PROJECT = "jdbc:google:mysql://moh-333:test/";

    public static final String USER_DATABASE = "USER";
    public Connection connection;

    public EntityManagerFactory getEntityManagerFactory(String database) {

        Map<String, String> properties = new HashMap<>();
        properties.put("javax.persistence.jdbc.driver",
                DRIVER);
        properties.put("javax.persistence.jdbc.url",
                PROJECT+database);
        properties.put("javax.persistence.jdbc.user",
                USER);
        properties.put("javax.persistence.jdbc.password",
                PASSWORD);

        return Persistence.createEntityManagerFactory(
                "mx.ohanahome.app.backend", properties);


    }

    public ResultSet select(String table, String columns[], String whereargs) throws SQLException {



        StringBuilder builder=new StringBuilder();
        builder.append("SELECT ");
        for(int i=0; i< columns.length;i++) {
            builder.append(columns[i]);
            if(i!=columns.length-1)
                builder.append(",");
        }
        builder.append(" FROM ");
        builder.append(table);
        builder.append(" WHERE ");
        builder.append(whereargs);

        return connection.createStatement().executeQuery(builder.toString());
    }

    public void stopConnection() throws SQLException {
        connection.close();
    }

}
