package mx.ohanahome.app.backend.util;

import java.util.HashSet;
import java.util.Set;

import mx.ohanahome.app.backend.entity.user.Permission;

/**
 * Created by ever on 18/04/16.
 */
public class Constants {


    public static class DB{
        public static final String DB_USER = "test_gae";
        public static final String DB_PASSWORD = "gae_psw";
        public static final String DB_DRIVER = "com.mysql.jdbc.GoogleDriver";
        public static final String DB_PROJECT = "jdbc:google:mysql://moh-333:test2/";

        public static final String USER_DATABASE = "USER_DB";
        public static final String INVITATION_DATABASE = "INVITATION_DB";
        public static final String PRODUCT_DATABASE = "PRODUCT_DB";
    }

    public static class CUser{
        public static final String MALE_GENDER = "Hombre";
        public static final String FEMALE_GENDER = "Mujer";

        public static final String EMAIL = "email";

        public static final String ID_USER = "user";

        public static final String DEFAULT_PICTURE_PATH = "http://www.ohanahome.mx/profile/images/%1$s.png";
    }
    public static class CIdentity{
        //columns names
        public static final String ID_ADAPTER = "id_adapter";
        public static final String ADAPTER = "adapter";
    }

    public static class CRole{
        public static final String ADMIN_ROLE = "Administrador";
        public static final String NORMAL_ROLE = "Normal";

    }
    public static class CInvitation {
        public static final String PENDING_INVITATION = "Pendiente";
        public static final String REJECTED_INVITATION = "Rechazada";
        public static final String ACCEPTED_INVITATION = "Aceptada";

        public static final String INVITATION_MSSG = "%1$s te ha invitado a su hogar";

        public static final String JOIN_HOME_MSSG = "%1$s se ha unido a tu hogar";

        public static final String INVITATION_LINK = "http://www.ohanahome.mx/invite/?iid=%1$s";

        public static final String INVITATION_MAIL = "Haz sido invitado a unirte al hogar %1$s en Ohana Home" +
                "<p> %2$s (%3$s) te ha invitado </p>" +
                "<p><a href='%4$s'>Unirte al hogar</a><br><a href='%4$s'>%4$s</a> </p>";

    }



    public static class CMessaging{
        public static final String INVITATION_TOPIC = "invite";
        public static final String JOIN_HOME_TOPIC = "join_home";
        public static final String ORDER_INVITATION_TOPIC = "order_invite";
        public static final String ORDER_PUSH_PRODUCT_TOPIC = "push_product";
        public static final String ORDER_PUSH_CUSTOMER_TOPIC = "push_customer";

        public static final String TOTAL_DESTRUCTION = "total_destruction";

    }

    public static class CPermission{

        public static class Admin{
        public static final Permission MODIFY_HOME_PERMISSION = new Permission(100,"Modificar datos del hogar");
        public static final Permission ADD_ADMIN_PERMISSION = new Permission( 101,"Nombrar a un administrador");
        public static final Permission REMOVE_ADMIN_PERMISSION = new Permission(102,"Eliminar a un administrador");
        public static final Permission QUIT_AS_ADMIN_PERMISSION = new Permission( 103,"Dejar de ser administrador");
        public static final Permission ADD_GUEST_PERMISSION = new Permission( 104,"Confirmar un nuevo miembro");
        public static final Permission DELETE_HOME_PERMISSION = new Permission( 105,"Eliminar un hogar");
        public static final Permission GRANT_PERMISSION = new Permission( 106,"Agregar Permiso");
        public static final Permission REVOKE_PERMISSION = new Permission( 107,"Quitar permiso");}
        public static class Normal{
        public static final Permission SEND_INVITATION_PERMISSION = new Permission(200,"Mandar invitaciones al hogar");
        public static final Permission ADD_PAYMENT_PERMISSION = new Permission(201,"Agregar método de pago");
        public static final Permission CREATE_ORDER_PERMISSION = new Permission( 202,"Crear una compra");
        public static final Permission MODIFY_ORDER_PERMISSION = new Permission( 203,"Modificar datos de compra");
        public static final Permission INVITE_GUEST_SHOP_PERMISSION = new Permission(204,"Invitar miembros a una compra");
        public static final Permission ASIGN_PRODUCT_PERMISSION = new Permission( 205,"Asignar un producto");
        public static final Permission REMOVE_GUEST_SHOP_PERMISSION = new Permission( 206,"Eliminar miembro de una compra");
        public static final Permission ADD_STOCK_ITEM_PERMISSION = new Permission(207,"Agregar producto al inventario");
        public static final Permission UPDATE_STOCK_ITEM_PERMISSION = new Permission(209,"Cambiar el estatus de un producto del inventario");}

    }

    public static class CUserRole{
        public static final String HOME = "home";
        public static final String USER = "user";
    }

    public static class COrder{
        public static final String ORDER_INVITATION_MSG = "%1$s te ha invitado a una compra";

    }

    public static class CCustomerOrder{
        public static final String PENDING_ORDER = "Pendiente";
        public static final String ACCEPTED_ORDER = "Aceptada";
        public static final String CANCELED_ORDER = "Cancelada";
        public static final String REJETCED_ORDER = "Rechazada";

        public static final String ADMIN_ROL = "Administrador";
        public static final String NORMAL_ROL = "Invitado";
    }

    public static class COrderStatus{
        public static final String PENDING_ORDER="Compra en proceso";
        public static final String CANCELED_ORDER ="Compra cancelada";
        public static final String COMPLETE_ORDER = "Compra finalizada";
        public static final String SENT_ORDER = "Compra enviada";
    }

    public static class COrderProduct{
        public static final String ID_ORDER_PRODUCT = "id_order_product";
        public static final String ID_PRODUCT = "id_product";
        public static final String ID_CUSTOMER = "id_customer";
        public static final String ID_ORDER = "id_order";
        public static final String QUANTITY ="quantity";
        public static final String PRICE = "price";

        public static final String ALL_COLUMNS [] = {ID_ORDER_PRODUCT,
        ID_PRODUCT,
        ID_CUSTOMER,
        ID_ORDER,
        QUANTITY,
        PRICE};
    }

    public static class CProduct{
        public static final String ID_PRODUCT="id_product";
        public static final String PRODUCT_NAME="product_name";
        public static final String ORDER_QUANTITY="order_quantity";
        public static final String CATEGORY = "category";
        public static final String SUB_CATEGORY = "sub_category";
        public static final String DEPTO = "depto";
        public static final String AMOUNT = "amount";
        public static final String UNIT = "unit";
        public static final String BRAND = "brand";
        public static final String PRODUCT_NO = "product_no";
        public static final String IMAGE = "image";
    }
}
