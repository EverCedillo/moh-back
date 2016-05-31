package mx.ohanahome.app.backend.entity.product;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



/**
 * Created by brenda on 5/1/16.
 */
@Table(name = "TOH_SHIPMENT")
@Entity
public class Shipment {
    @GeneratedValue(generator = "increment")
    @Id
    @Column(name = "id_shipment")

    @Temporal(TemporalType.TIMESTAMP)
    Date shipment_date;

    @OneToMany(mappedBy="shipments")
    private Set<Shipment_Status> shipment_status;

    @OneToMany(mappedBy=" shipment_payment")
    private Set<Payment> payments;


    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="id_order")
    private Order shipment_order;


    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="id_order_product")
    private OrderProduct orderProduct;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_store")
    private Store store;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="id_home")
    private Home home_shipment;



    String shipment_details;
    private long id_shipment;
    private long id_order;


    public long getId_shipment() {
        return id_shipment;
    }

    public void setId_shipment(long id_shipment) {
        this.id_shipment = id_shipment;
    }

    public long getId_order() {
        return id_order;
    }

    public void setId_order(long id_order) {
        this.id_order = id_order;
    }

    public Date getShipment_date() {
        return shipment_date;
    }

    public void setShipment_date(Date shipment_date) {
        this.shipment_date = shipment_date;
    }

    public String getShipment_details() {
        return shipment_details;
    }

    public void setShipment_details(String shipment_details) {
        this.shipment_details = shipment_details;
    }



}
