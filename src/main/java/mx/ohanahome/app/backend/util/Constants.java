package mx.ohanahome.app.backend.util;

/**
 * Created by ever on 18/04/16.
 */
public class Constants {
    public static final String UNIVERSAL_ALIAS = "a";

    public static final String USER_DATABASE = "USER_DB";

    public enum TOH_USER{
        ID("id"),EMAIL("email"),ID_ADAPTER("id_adapter"),ADAPTER("adapter"),ID_IDENTIFY("id_identify");


        TOH_USER(String name) {
            this.name=UNIVERSAL_ALIAS+"."+name;
        }

        public String name;
    }
}
