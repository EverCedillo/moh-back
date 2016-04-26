package mx.ohanahome.app.backend.entity.user;

;import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "T_REG_RECORD")
@Entity
public class RegistrationRecord {

    @GeneratedValue(generator = "increment")
    @Id
    long id;


    String regId;

    public RegistrationRecord(long id, String regId) {
        this.id = id;
        this.regId = regId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RegistrationRecord() {}

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }
}