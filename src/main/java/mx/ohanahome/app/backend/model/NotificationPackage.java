package mx.ohanahome.app.backend.model;

import java.util.HashMap;

import mx.ohanahome.app.backend.entity.user.RegistrationRecord;

/**
 * Created by ever on 4/08/16.
 */
public class NotificationPackage {
    RegistrationRecord registrationRecord;
    HashMap<String,String> extras;


    public RegistrationRecord getRegistrationRecord() {
        return registrationRecord;
    }

    public void setRegistrationRecord(RegistrationRecord registrationRecord) {
        this.registrationRecord = registrationRecord;
    }

    public HashMap<String, String> getExtras() {
        return extras;
    }

    public void setExtras(HashMap<String, String> extras) {
        this.extras = extras;
    }
}
