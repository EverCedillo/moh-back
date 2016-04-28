package mx.ohanahome.app.backend.util;

/**
 * Created by ever on 18/04/16.
 */
public class Constants {
    public static final String USER_DATABASE = "USER_DB";

    public static class DB{
        public static final String DB_USER = "test_gae";
        public static final String DB_PASSWORD = "gae_psw";
        public static final String DB_DRIVER = "com.mysql.jdbc.GoogleDriver";
        public static final String DB_PROJECT = "jdbc:google:mysql://moh-333:test2/";
    }

    public static class CIdentity{
        //columns names
        public static final String ID_ADAPTER = "id_adapter";
        public static final String ADAPTER = "adapter";
    }

    public static class CRole{
        public static String ADMIN_ROLE = "Administrador";
        public static String NORMAL_ROLE = "Normal";

    }

}
