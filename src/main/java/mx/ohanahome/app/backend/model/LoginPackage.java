package mx.ohanahome.app.backend.model;

import java.sql.Date;

import mx.ohanahome.app.backend.entity.Identify;
import mx.ohanahome.app.backend.entity.User;

/**
 * Created by ever on 18/04/16.
 */
public class LoginPackage {
    private User user;
    private Identify identify;

    public LoginPackage(User user, String id_adapter, String adapter, String email_, Date creationDate, Date modificationDate){
        this.user = user;
        identify = new Identify(id_adapter,adapter,email_,creationDate,modificationDate);
    }

    public LoginPackage(){}

    public LoginPackage(User user, Identify identify) {
        this.user = user;
        this.identify = identify;
    }

    public Identify getIdentify() {
        return identify;
    }

    public User getUser() {
        return user;
    }
}
