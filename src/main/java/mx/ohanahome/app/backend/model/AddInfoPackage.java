package mx.ohanahome.app.backend.model;

import mx.ohanahome.app.backend.entity.user.Identify;
import mx.ohanahome.app.backend.entity.user.Illness;
import mx.ohanahome.app.backend.entity.user.Intolerance;

/**
 * Created by ever on 9/05/16.
 */
public class AddInfoPackage {
    Identify identify;
    Illness illness;
    Intolerance intolerance;

    public Identify getIdentify() {
        return identify;
    }

    public Illness getIllness() {
        return illness;
    }

    public Intolerance getIntolerance() {
        return intolerance;
    }
}
