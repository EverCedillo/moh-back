package mx.ohanahome.app.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.repackaged.com.google.api.client.util.Base64;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

import javax.inject.Named;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import mx.ohanahome.app.backend.entity.invitation.Home;
import mx.ohanahome.app.backend.entity.invitation.Invitation;
import mx.ohanahome.app.backend.entity.user.Identify;
import mx.ohanahome.app.backend.entity.user.Permission;
import mx.ohanahome.app.backend.entity.user.RegistrationRecord;
import mx.ohanahome.app.backend.entity.user.Role;
import mx.ohanahome.app.backend.entity.user.User;
import mx.ohanahome.app.backend.entity.user.UserRole;
import mx.ohanahome.app.backend.model.InvitationPackage;
import mx.ohanahome.app.backend.model.NotificationPackage;
import mx.ohanahome.app.backend.util.Constants;
import mx.ohanahome.app.backend.util.EMFInvitation;
import mx.ohanahome.app.backend.util.EMFUser;
import mx.ohanahome.app.backend.util.MOHException;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "invitationApi",
        version = "v1",
        resource = "invitation",
        namespace = @ApiNamespace(
                ownerDomain = "backend.app.ohanahome.mx",
                ownerName = "backend.app.ohanahome.mx",
                packagePath = ""
        )
)
public class InvitationEndpoint {

    private static final Logger logger = Logger.getLogger(InvitationEndpoint.class.getName());

    /**
     * This method gets the <code>Invitation</code> object associated with the specified <code>id</code>.
     *
     * @param id The id of the object to be returned.
     * @return The <code>Invitation</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getInvitation")
    public Invitation getInvitation(@Named("id") Long id) {

        logger.info("Calling getInvitation method");
        return null;
    }

    /**
     * This inserts a new <code>Invitation</code> object.
     *
     * @param invitationPackage The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "sendInvitation")
    public Invitation sendInvitation(InvitationPackage invitationPackage) throws MOHException{

        EntityManager manager= EMFInvitation.get().createEntityManager();

        EntityManager manager1 = EMFUser.get().createEntityManager();
        Invitation invitation;

        try {

            invitation = invitationPackage.getInvitation();
            Status status;
            status = validateFields(invitationPackage);
            if (status != Status.OK) throw new MOHException(status.getMessage(), status.getCode());


            status = verifyIdentity(invitationPackage, manager1);
            if (status != Status.OK)
                throw new MOHException(status.getMessage(), status.getCode());
            Identify sender = (Identify) status.getResponse();



            mx.ohanahome.app.backend.entity.user.Home h = new mx.ohanahome.app.backend.entity.user.Home();

            h = manager1.find(mx.ohanahome.app.backend.entity.user.Home.class, invitation.getHome().getId_home());
            Set<UserRole> userRoles=h.getUserRoles();
            UserRole userRole = null;
            for(UserRole ur: userRoles){
                if(ur.getUser().getId_user()==sender.getUser().getId_user())
                    userRole=ur;
            }

            if(userRole==null) throw new MOHException("Auth error", MOHException.STATUS_AUTH_ERROR);
            
            Role role = userRole.getRole();
            if (role == null)
                throw new MOHException("Auth error", MOHException.STATUS_AUTH_ERROR);
            if (role.getPermissions().isEmpty())
                throw new MOHException("Auth error", MOHException.STATUS_AUTH_ERROR);
            Permission p = manager1.find(Permission.class, Constants.CPermission.Normal.SEND_INVITATION_PERMISSION.getId_permission());
            if (!role.getPermissions().contains(p))
                throw new MOHException("The user does not have the permission", MOHException.STATUS_OBJECT_NOT_ACCESSIBLE);


            String email = invitationPackage.getInvitation().getEmail_to();

            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.MONTH, 2);


            invitation.setExpiration_date(c.getTime());
            Home home = manager.find(Home.class, invitation.getHome().getId_home());
            invitation.setHome(home);
            invitation.setStatus(Constants.CInvitation.PENDING_INVITATION);

            invitation.setEmail_to(email);


            EntityTransaction transaction = manager.getTransaction();
            transaction.begin();
            manager.persist(invitation);

            manager.flush();


            TypedQuery<User> query1 = manager1.createNamedQuery("User.findUserByEmail", User.class);

            query1.setParameter(Constants.CUser.EMAIL, email);
            List<User> users = query1.getResultList();
            User user = users.isEmpty() ? null : users.get(0);
            String real = invitationPackage.getInvitation().getId_invitation() + "-"+sender.getUser().getUser_name()+ "-" +sender.getUser().getPicture() +"-"+invitation.getHome().getHome_name();
            StringBuilder builder = new StringBuilder(Base64.encodeBase64URLSafeString(real.getBytes(Charset.forName("UTF-8"))));
            String encode = builder.reverse().toString();
            if (user != null) {
                if (user.getRecords() != null) {

                    Set<RegistrationRecord> records = user.getRecords();
                    HashMap<String,String> extras = new HashMap<>();
                    extras.put("extra",encode);

                    for (RegistrationRecord r : records) {
                        NotificationPackage notificationPackage = new NotificationPackage();
                        notificationPackage.setExtras(extras);
                        notificationPackage.setRegistrationRecord(r);
                        Formatter f = new Formatter();
                        try {
                            new MessagingEndpoint().sendMessage(
                                    f.format(
                                            Constants.CInvitation.INVITATION_MSSG,
                                            invitationPackage.getInvitation().getSender_name()).toString(),
                                    notificationPackage,
                                    Constants.CMessaging.INVITATION_TOPIC);
                        } catch (IOException e) {
                            e.printStackTrace();
                            transaction.setRollbackOnly();
                        }
                    }

                }
            }//else throw new MOHException("HOli", 1);

            String content = Constants.CInvitation.INVITATION_MAIL;
            String link = Constants.CInvitation.INVITATION_LINK;


            Formatter formatLink = new Formatter();
            Formatter formatSubject = new Formatter();
            Formatter formatContent = new Formatter();
            content = formatContent.format(content,
                    invitation.getHome().getHome_name(),
                    invitation.getSender_name(),
                    sender.getUser().getEmail(),
                    formatLink.format(link, encode).toString()).toString();
            try {
                sendEmail(email,
                        formatSubject.format(Constants.CInvitation.INVITATION_MSSG, invitation.getSender_name()).toString(),
                        content);
            } catch (UnsupportedEncodingException | MessagingException e) {
                e.printStackTrace();
                transaction.setRollbackOnly();

            }


            transaction.commit();
        }finally {
            if(manager.getTransaction().isActive())
                manager.getTransaction().rollback();
            if(manager1.getTransaction().isActive())
                manager1.getTransaction().rollback();

            manager.close();
            manager1.close();
        }

        return invitation;
    }

    @ApiMethod(name = "resolveInvitation")
    public Invitation resolveInvitation(@Named("iid") String iid,@Named("status") String state, Identify identify) throws MOHException{
        Status status;
        /*status=validateFields(invitationPackage);
        if(status!=Status.OK) throw  new MOHException(status.getMessage(),status.getCode());*/


        EntityManager invitationManager = EMFInvitation.get().createEntityManager();
        EntityManager userManager= EMFUser.get().createEntityManager();
        Invitation invitation;

        try {

            status = verifyIdentity(new InvitationPackage(identify), userManager);
            if (status != Status.OK)
                throw new MOHException(status.getMessage(), status.getCode());

            identify = (Identify) status.getResponse();

            StringBuilder builder = new StringBuilder(iid);
            String rid = builder.reverse().toString();
            long id;
            try {

                id = Long.valueOf(new String(Base64.decodeBase64(rid)));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                throw new MOHException("Object not accessible," + rid + "," + new String(Base64.decodeBase64(rid)), MOHException.STATUS_OBJECT_NOT_ACCESSIBLE);
            }


            invitation = invitationManager.find(Invitation.class, id);
            if (invitation == null)
                throw new MOHException("Not accessible", MOHException.STATUS_OBJECT_NOT_ACCESSIBLE);

            if (invitation.getStatus().equals(Constants.CInvitation.ACCEPTED_INVITATION) ||
                    invitation.getStatus().equals(Constants.CInvitation.REJECTED_INVITATION))
                throw new MOHException("Invitation has expired", MOHException.STATUS_OBJECT_NOT_ACCESSIBLE);

            Calendar date = Calendar.getInstance();
            date.setTime(invitation.getExpiration_date());
            Calendar actual = Calendar.getInstance();
            actual.setTime(new Date());
            if (date.compareTo(actual) <= 0)
                throw new MOHException("Invitation has expired", MOHException.STATUS_OBJECT_NOT_ACCESSIBLE);

            if (state.equals(Constants.CInvitation.REJECTED_INVITATION)) {
                invitationManager.getTransaction().begin();
                invitation.setStatus(Constants.CInvitation.REJECTED_INVITATION);
                invitationManager.persist(invitation);
                invitationManager.getTransaction().commit();
                return invitation;
            }


            mx.ohanahome.app.backend.entity.user.Home home = userManager.find(mx.ohanahome.app.backend.entity.user.Home.class, invitation.getHome().getId_home());
            if (home == null)
                throw new MOHException("Missing fields", MOHException.STATUS_NOT_ENOUGH_DATA);


            Role role = new Role(Constants.CRole.NORMAL_ROLE);
            Set<Permission> ps = Permission.getNormalPermissions();
            Set<Permission> permissions = new HashSet<>();
            for (Permission p : ps) {
                Permission permission = userManager.find(Permission.class, p.getId_permission());
                if (permission != null) permissions.add(permission);
            }


            EntityTransaction transaction = userManager.getTransaction();
            transaction.begin();

            User user = identify.getUser();

            home.setModification_date(home.getModification_date());
            role.addPermissions(permissions);

            UserRole userRole = new UserRole();
            userRole.setStart_date(new Date());
            userRole.setModification_date(new Date());
            userRole.setUser(user);
            userRole.setRole(role);

            Set<UserRole> users = home.getUserRoles();
            home.addUserRole(userRole);


            try {
                invitationManager.getTransaction().begin();
                invitation.setStatus(Constants.CInvitation.ACCEPTED_INVITATION);
                invitationManager.persist(invitation);
                invitationManager.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
                transaction.setRollbackOnly();
            }

            userManager.persist(home);

            user.addHome(home);
            userManager.persist(user);



            Formatter f = new Formatter();

            for (UserRole ur : users) {
                Set<RegistrationRecord> records = new HashSet<>();
                if(ur.getUser().getId_user()!=user.getId_user())
                    records = ur.getUser().getRecords();
                HashMap<String,String> extras = new HashMap<>();

                extras.put("extra",String.valueOf(invitation.getHome().getId_home()));
                extras.put(Constants.CUser.USER_NAME,user.getUser_name());
                extras.put(Constants.CUser.PICTURE,user.getPicture());
                extras.put(Constants.CUser.ID_USER,String.valueOf(user.getId_user()));
                extras.put(Constants.CRole.ROLE_NAME,role.getRole_name());
                extras.put(Constants.CHome.HOME_ID,String.valueOf(home.getId_home()));
                for (RegistrationRecord r : records)
                    try {
                        NotificationPackage notificationPackage = new NotificationPackage();
                        notificationPackage.setExtras(extras);
                        notificationPackage.setRegistrationRecord(r);
                        if (r != null)
                            new MessagingEndpoint().sendMessage(f.format(
                                            Constants.CInvitation.JOIN_HOME_MSSG, user.getUser_name()).toString(),
                                    notificationPackage,
                                    Constants.CMessaging.JOIN_HOME_TOPIC);
                    } catch (IOException e) {
                        e.printStackTrace();
                        //transaction.setRollbackOnly();

                    }
            }
            transaction.commit();

        }finally {
            invitationManager.close();
            userManager.close();
        }

        return invitation;
    }

    private void sendEmail(String to,String subject,String content) throws UnsupportedEncodingException, MessagingException {
        Properties properties = new Properties();
        Session session = Session.getDefaultInstance(properties,null);


        Message message = new MimeMessage(session);

            Multipart mp = new MimeMultipart();
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(content, "text/html");
            mp.addBodyPart(htmlPart);

            message.setFrom(new InternetAddress("ever@ohanahome.mx", "Ohana Home"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            ((MimeMessage) message).setSubject(subject,"UTF-8");
            message.setContent(mp);
            Transport.send(message);

    }


    private Status verifyIdentity(InvitationPackage invitationPackage, EntityManager manager){

        TypedQuery<Identify> query = manager.createNamedQuery("Identify.verifyIdentity",Identify.class);
        Identify id = invitationPackage.getIdentify();
        query.setParameter(Constants.CIdentity.ADAPTER,id.getAdapter());
        query.setParameter(Constants.CIdentity.ID_ADAPTER,id.getId_adapter());

        List<Identify> ids=query.getResultList();
        id=ids.isEmpty()?null:ids.get(0);

        if(id==null) return Status.WRONG_USER;
        return Status.OK.withResponse(id);
    }
    private Status validateFields(InvitationPackage invitationPackage){

        if(false)
        {
            return Status.NOT_ENOUGH_DATA;
        }
        return Status.OK;
    }

    enum Status{
        WRONG_USER(MOHException.STATUS_OBJECT_NOT_FOUND,"User not found"),
        USER_ALREADY_EXISTS(MOHException.STATUS_OBJECT_NOT_ACCESSIBLE,"User already exists"),
        NOT_ENOUGH_DATA(MOHException.STATUS_NOT_ENOUGH_DATA,"Missing fields"),
        OK(3,"Status OK");

        private int code;
        private String message;
        private Object response;



        Status(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }
        public Status withResponse(Object response){
            this.response= response;
            return this;
        }

        public String getMessage() {
            return message;
        }

        public Object getResponse() {
            return response;
        }
    }
}
