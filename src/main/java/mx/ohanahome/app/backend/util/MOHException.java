package mx.ohanahome.app.backend.util;

import javax.persistence.EntityManager;

/**
 * Created by ever on 18/04/16.
 */
public class MOHException extends Exception {
    private String message;
    private int code;

    public static final int STATUS_SERVER_ERROR= 0;
    public static final int STATUS_NOT_ENOUGH_DATA = 1;
    public static final int STATUS_OBJECT_NOT_FOUND = 2;
    public static final int STATUS_OBJECT_NOT_ACCESSIBLE = 3;
    public static final int STATUS_AUTH_ERROR = 4;

    public MOHException(String mssg, int code){
        super(mssg);
        message=mssg;
        this.code=code;

    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
