package mx.ohanahome.app.backend.model;

import java.util.HashMap;
import java.util.Map;

import mx.ohanahome.app.backend.entity.user.Home;
import mx.ohanahome.app.backend.entity.user.Identify;

/**
 * Created by ever on 24/04/16.
 */
public class HomePackage {
    private Home home;
    private Identify identify;


    public Home getHome() {
        return home;
    }

    public Identify getIdentify() {
        return identify;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    public void setIdentify(Identify identify) {
        this.identify = identify;
    }
}
