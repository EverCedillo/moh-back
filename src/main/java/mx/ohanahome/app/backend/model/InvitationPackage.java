package mx.ohanahome.app.backend.model;

import mx.ohanahome.app.backend.entity.invitation.Invitation;
import mx.ohanahome.app.backend.entity.user.Identify;
import mx.ohanahome.app.backend.entity.user.User;

/**
 * Created by ever on 29/04/16.
 */
public class InvitationPackage {
    Invitation invitation;
    Identify identify;

    public Invitation getInvitation() {
        return invitation;
    }

    public void setInvitation(Invitation invitation) {
        this.invitation = invitation;
    }

    public Identify getIdentify() {
        return identify;
    }

    public void setIdentify(Identify identify) {
        this.identify = identify;
    }

    public InvitationPackage(Identify identify) {
        this.identify = identify;
    }

    public InvitationPackage(){}
}
