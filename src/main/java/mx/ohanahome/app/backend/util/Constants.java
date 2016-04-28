package mx.ohanahome.app.backend.util;

import java.util.HashSet;
import java.util.Set;

import mx.ohanahome.app.backend.entity.user.Permission;

/**
 * Created by ever on 18/04/16.
 */
public class Constants {


    public static class DB{
        public static final String DB_USER = "test_gae";
        public static final String DB_PASSWORD = "gae_psw";
        public static final String DB_DRIVER = "com.mysql.jdbc.GoogleDriver";
        public static final String DB_PROJECT = "jdbc:google:mysql://moh-333:test2/";

        public static final String USER_DATABASE = "USER_DB";
    }

    public static class CUser{
        public static final String MALE_GENDER = "Hombre";
        public static final String FEMALE_GENDER = "Mujer";
    }
    public static class CIdentity{
        //columns names
        public static final String ID_ADAPTER = "id_adapter";
        public static final String ADAPTER = "adapter";
    }

    public static class CRole{
        public static final String ADMIN_ROLE = "Administrador";
        public static final String NORMAL_ROLE = "Normal";

    }

    public static class CPermission{
        public static final String GRANT_PERMISSION = "??";
        public static final String REVOKE_PERMISSION = "???";
        public static final String SHOP_PERMISSION = "???";

    }

    public static class CUserRole{
        public static final String ID_HOME = "id_home";
        public static final String ID_USER = "id_user";
    }

}
