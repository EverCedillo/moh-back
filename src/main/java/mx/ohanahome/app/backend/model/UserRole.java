package mx.ohanahome.app.backend.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Created by brenda on 4/3/16.
 */
@Table(name = "TOH_USER_ROLE")
@Entity
public class UserRole {

    @Id
    long id_user_role;
    long id_role;
    long id_user;

    @Temporal(TemporalType.TIMESTAMP)
    Date role_start_date;
    @Temporal(TemporalType.TIMESTAMP)
    Date modification_date;
    @Temporal(TemporalType.TIMESTAMP)
    Date role_end_date;

    public long getId_user_role() {
        return id_user_role;
    }

    public void setId_user_role(long id_user_role) {
        this.id_user_role = id_user_role;
    }

    public long getId_role() {
        return id_role;
    }

    public void setId_role(long id_role) {
        this.id_role = id_role;
    }

    public long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    public Date getRole_start_date() {
        return role_start_date;
    }

    public void setRole_start_date(Date role_start_date) {
        this.role_start_date = role_start_date;
    }

    public Date getModification_date() {
        return modification_date;
    }

    public void setModification_date(Date modification_date) {
        this.modification_date = modification_date;
    }

    public Date getRole_end_date() {
        return role_end_date;
    }

    public void setRole_end_date(Date role_end_date) {
        this.role_end_date = role_end_date;
    }

    public UserRole(){}
}
