package mx.ohanahome.app.backend.model;

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
}