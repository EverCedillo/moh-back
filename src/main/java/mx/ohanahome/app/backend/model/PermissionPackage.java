package mx.ohanahome.app.backend.model;

import java.util.HashSet;
import java.util.Set;

import mx.ohanahome.app.backend.entity.user.Home;
import mx.ohanahome.app.backend.entity.user.Identify;
import mx.ohanahome.app.backend.entity.user.Permission;

/**
 * Created by ever on 26/04/16.
 */
public class PermissionPackage {
    Identify grants;
    Identify recipient;
    Home home;
    Set<Permission> permissions=new HashSet<>();

    public Identify getGrants() {
        return grants;
    }

    public void setGrants(Identify grants) {
        this.grants = grants;
    }

    public Identify getRecipient() {
        return recipient;
    }

    public void setRecipient(Identify recipient) {
        this.recipient = recipient;
    }

    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void addPermission(Permission permission){
        permissions.add(permission);
    }
}
