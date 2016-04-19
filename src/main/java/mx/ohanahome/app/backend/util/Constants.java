package mx.ohanahome.app.backend.util;

/**
 * Created by ever on 18/04/16.
 */
public class Constants {

    public enum TOH_USER{
        ID("id"),EMAIL("email"),ID_ADAPTER("id_adapter"),ADAPTER("adapter");


        TOH_USER(String name) {
            this.name=name;
        }
        private String name;
    }
}
