package mx.ohanahome.app.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
//import com.sun.org.apache.xerces.internal.impl.xpath.regex.RegularExpression;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import mx.ohanahome.app.backend.entity.user.Identify;
import mx.ohanahome.app.backend.entity.user.Illness;
import mx.ohanahome.app.backend.entity.user.Intolerance;
import mx.ohanahome.app.backend.entity.user.User;
import mx.ohanahome.app.backend.model.AddInfoPackage;
import mx.ohanahome.app.backend.model.LoginPackage;
import mx.ohanahome.app.backend.util.Constants;
import mx.ohanahome.app.backend.util.EMFUser;
import mx.ohanahome.app.backend.util.MOHException;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "userApi",
        version = "v1",
        resource = "user",
        namespace = @ApiNamespace(
                ownerDomain = "backend.app.ohanahome.mx",
                ownerName = "backend.app.ohanahome.mx",
                packagePath = ""
        )
)
public class UserEndpoint {

    private static final Logger logger = Logger.getLogger(UserEndpoint.class.getName());

    public static final int FIND_BY_ID = 0;
    public static final int FIND_BY_IDENTITY = 1;
    public static final int UPDATE_USER = 2;
    public static final int INSERT_USER = 3;
    public static final int ADD_INFO = 4;

    /**
     * This method gets the <code>User</code> object associated with the specified <code>id</code>.
     *
     * @param loginPackage The id of the <code>User</code> or <code>Identify</code> object.
     * @return The <code>User</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getUser",httpMethod = ApiMethod.HttpMethod.GET)
    public User getUser(LoginPackage loginPackage) throws MOHException{

        EntityManager manager = EMFUser.get().createEntityManager();
        User user = null;
        try {
            int flag = FIND_BY_IDENTITY;
            Identify identify = loginPackage.getIdentify();

            switch (flag) {
                case FIND_BY_ID:
                    identify = manager.find(Identify.class, identify.getId_identify());
                    user = identify.getUser();
                    break;
                case FIND_BY_IDENTITY:
                    Status status = verifyIdentity(identify, manager);
                    if (status != Status.OK)
                        throw new MOHException(status.getMessage(), status.getCode());
                    identify = (Identify) status.getResponse();
                    user = identify.getUser();
                    break;
            }
            if (user == null) {
                manager.close();
                throw new MOHException(Status.WRONG_USER.getMessage(), Status.WRONG_USER.getCode());
            }
        }finally {
            manager.close();
        }



        return user;
    }

    /**
     * This update a existent <code>User</code> object
     *
     * @param  loginPackage Package with <code>User</code> and <code>Identify</code> object to be updated.
     * @return The object updated
     */
    @ApiMethod(name = "updateUser")
    public User updateUser (LoginPackage loginPackage)throws MOHException{

        EntityManager manager = EMFUser.get().createEntityManager();
        Status status;
        User user;
        try {

            status = validateFields(loginPackage, UPDATE_USER);
            if (status != Status.OK)
                throw new MOHException(status.getMessage(), status.getCode());
            status = verifyIdentity(loginPackage.getIdentify(), manager);
            if (status != Status.USER_ALREADY_EXISTS)
                throw new MOHException(status.getMessage(), status.getCode());
            Identify identify = (Identify) status.getResponse();

            user = identify.getUser();
            manager.getTransaction().begin();
            user.mergeValues(loginPackage.getUser());
            user.setModification_date(new java.util.Date());

            manager.persist(user);
            manager.getTransaction().commit();
        }finally {
            manager.close();
        }

        return user;
    }

    /**
     * This inserts a new <code>User</code> object.
     *
     * @param loginPackage Package with <code>User</code> and <code>Identify</code> object.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertUser")
    public Identify insertUser(LoginPackage loginPackage) throws MOHException{


        Status status;
        Identify identify = loginPackage.getIdentify();
        EntityManager manager =EMFUser.get().createEntityManager();

        try {
            status = validateFields(loginPackage, INSERT_USER);
            if (status != Status.OK)
                throw new MOHException(status.getMessage(), status.getCode());


            status = verifyIdentity(identify, manager);
            if (status != Status.OK)
                throw new MOHException(status.getMessage(), status.getCode());


            User user = loginPackage.getUser();

            TypedQuery<User> userTypedQuery = manager.createNamedQuery("User.findUserByEmail", User.class);
            userTypedQuery.setParameter(Constants.CUser.EMAIL, user.getEmail());
            if (!userTypedQuery.getResultList().isEmpty())
                throw new MOHException("User with email " + user.getEmail() + " already exists", MOHException.STATUS_OBJECT_NOT_ACCESSIBLE);


            manager.getTransaction().begin();

            user.setCreation_date(new java.util.Date());
            user.setModification_date(new java.util.Date());
            user.setPicture("none");
            manager.persist(user);


            identify.setCreation_date(new java.util.Date());
            identify.setModification_date(new java.util.Date());
            identify.setUser(user);
            manager.persist(identify);

            manager.flush();

            user.setPicture(String.format(Constants.CUser.DEFAULT_PICTURE_PATH, String.valueOf(user.getId_user())));
            manager.persist(user);

            manager.getTransaction().commit();

        }finally {
            manager.close();
        }


        return identify;
    }

    @ApiMethod(path = "me")
    public User findUser(LoginPackage loginPackage) throws MOHException{
        Identify identify = loginPackage.getIdentify();
        User user=null;

        EntityManager manager = EMFUser.get().createEntityManager();
        try {

            Status status = verifyIdentity(identify, manager);
            if (status != Status.USER_ALREADY_EXISTS) return null;
            identify = (Identify) status.getResponse();
            user = identify.getUser();

            if (user == null)
                throw new MOHException(Status.WRONG_USER.getMessage(), Status.WRONG_USER.getCode());

        }finally {
            manager.close();
        }

        return user;
    }

    @ApiMethod(path = "me/info")
    public void addMedInfo(AddInfoPackage infoPackage) throws MOHException{
        EntityManager userManager = EMFUser.get().createEntityManager();
        Status status;

        try {
            status = verifyIdentity(infoPackage.getIdentify(), userManager);
            if (status != Status.OK) throw new MOHException(status.getMessage(), status.getCode());
            Identify identify = (Identify) status.getResponse();

            userManager.getTransaction().begin();

            User user = identify.getUser();
            if (infoPackage.getIllness() != null) {
                Illness illness = userManager.find(Illness.class, infoPackage.getIllness().getId_illness());
                user.addIllness(illness);
            }
            if (infoPackage.getIntolerance() != null) {
                Intolerance intolerance = userManager.find(Intolerance.class, infoPackage.getIntolerance().getId_intolerance());
                user.addIntolerance(intolerance);
            }

            userManager.persist(user);

            userManager.getTransaction().commit();
        }finally {
            userManager.close();
        }

    }

    private Status verifyIdentity(Identify identify, EntityManager manager){

        TypedQuery<Identify> query = manager.createNamedQuery("Identify.verifyIdentity",Identify.class);

        query.setParameter(Constants.CIdentity.ID_ADAPTER,identify.getId_adapter());
        query.setParameter(Constants.CIdentity.ADAPTER,identify.getAdapter());
        List<Identify> ids = query.getResultList();
        Identify ident = ids.isEmpty()?null:ids.get(0);
        if(ident!=null) return Status.USER_ALREADY_EXISTS.withResponse(ident);
        else return Status.OK;
    }

    private Status validateFields(LoginPackage loginPackage, int flag){
        User user;
        Identify identify;
        //RegularExpression expression = new RegularExpression("[a-zA-Z_-0-9]+@[a-zA-Z-_0-9]+(.[a-zA-Z])+");
        /*
        switch (flag){
            case INSERT_USER:
                user=loginPackage.getUser();
                if(user==null) return Status.NOT_ENOUGH_DATA;
                if(user.getEmail()==null||
                        user.getGender()==null||
                        user.getLast_name()==null||
                        user.getUser_name()==null||
                        user.getPicture()==null||
                        user.getBirthday()==null) return Status.NOT_ENOUGH_DATA;
                if(!user.getGender().equals(Constants.CUser.FEMALE_GENDER)||
                        !user.getGender().equals(Constants.CUser.MALE_GENDER))
                    return Status.NOT_ENOUGH_DATA;

                if(!expression.matches(user.getEmail())) return Status.NOT_ENOUGH_DATA;

                identify=loginPackage.getIdentify();
                if(identify==null) return Status.NOT_ENOUGH_DATA;
                if(identify.getEmail()==null) return Status.NOT_ENOUGH_DATA;
                if(!expression.matches(identify.getEmail())) return Status.NOT_ENOUGH_DATA;

                break;
            case UPDATE_USER:
                break;
        }*/
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


        Status(int code, String message, Object response) {
            this.code = code;
            this.message = message;
            this.response = response;
        }

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