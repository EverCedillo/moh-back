package mx.ohanahome.app.backend.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ever on 3/04/16.
 */
public class DbConection {
    public static final String USER = "gae";
    public static final String PASSWORD = "gae_pwd";
    public static final String DRIVER = "com.mysql.jdbc.GoogleDriver";
    public static final String PROJECT = "jdbc:google:mysql://moh-333:test/";

    public static Connection connection;

    public static boolean startConnection(String database) throws SQLException, ClassNotFoundException {

        Class.forName(DRIVER);
        connection = DriverManager.getConnection(
                PROJECT+database,
                USER,
                PASSWORD
        );
        return connection!=null;
    }

    public static ResultSet select(String table, String columns[], String whereargs) throws SQLException {
        StringBuilder builder=new StringBuilder();
        builder.append("SELECT");
        for(String s: columns) {
            builder.append(s);
            builder.append(",");
        }
        builder.append("FROM");
        builder.append(table);
        builder.append("WHERE");
        builder.append(whereargs);

        return connection.createStatement().executeQuery(builder.toString());
    }

}
