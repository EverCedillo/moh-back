package mx.ohanahome.app.backend.util;

/**
 * Created by ever on 21/08/16.
 */
public class AUser extends com.google.api.server.spi.auth.common.User {
    public AUser(String email) {
        super(email);
    }

    public AUser(String id, String email) {
        super(id, email);
    }
}
