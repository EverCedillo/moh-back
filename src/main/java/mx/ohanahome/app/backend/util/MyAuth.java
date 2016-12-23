package mx.ohanahome.app.backend.util;


import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.Authenticator;


import javax.servlet.http.HttpServletRequest;


/**
 * Created by ever on 21/08/16
 */
public class MyAuth implements Authenticator {

    @Override
    public User authenticate(HttpServletRequest httpServletRequest) {
        return null;
    }
}
