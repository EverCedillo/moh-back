package mx.ohanahome.app.backend.entity.product;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Created by brenda on 5/1/16.
 */
@Table(name = "TOH_SHIPMENT_STATUS")
@Entity
public class Shipment_Status {
    @GeneratedValue(generator = "increment")
    @Id
    @Column(name="id_shipment_status")
    private  long id_shipment_status;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="id_shipment")
    private Shipment shipments;


    @Temporal(TemporalType.TIMESTAMP)
    Date shipment_date;

    String status_name;

    public Shipment_Status(String status_name){
        this.status_name= status_name;
    }



    public long getId_shipment_status() {
        return id_shipment_status;
    }

    public void setId_shipment_status(long id_shipment_status) {
        this.id_shipment_status = id_shipment_status;
    }

    public Date getShipment_date() {
        return shipment_date;
    }

    public void setShipment_date(Date shipment_date) {
        this.shipment_date = shipment_date;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }
}
