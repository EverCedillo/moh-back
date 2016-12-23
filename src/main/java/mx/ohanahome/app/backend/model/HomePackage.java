package mx.ohanahome.app.backend.model;

import java.util.HashMap;
import java.util.Map;

import mx.ohanahome.app.backend.entity.user.Home;
import mx.ohanahome.app.backend.entity.user.Identify;
import mx.ohanahome.app.backend.entity.user.Scales;
import mx.ohanahome.app.backend.entity.user.User;
import mx.ohanahome.app.backend.entity.user.UserRole;

/**
 * Created by ever on 24/04/16.
 */
public class HomePackage {
    private Home home;
    private Identify identify;
    private UserRole guest;
    private Scales scales;


    public Home getHome() {
        return home;
    }

    public Identify getIdentify() {
        return identify;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    public UserRole getGuest() {
        return guest;
    }

    public void setGuest(UserRole guest) {
        this.guest = guest;
    }

    public void setIdentify(Identify identify) {
        this.identify = identify;
    }

    public Scales getScales() {
        return scales;
    }

    public void setScales(Scales scales) {
        this.scales = scales;
    }
}
