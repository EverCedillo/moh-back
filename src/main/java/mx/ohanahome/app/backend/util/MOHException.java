package mx.ohanahome.app.backend.util;

/**
 * Created by ever on 18/04/16.
 */
public class MOHException extends Exception {
    private String message;
    public MOHException(String mssg){
        super(mssg);
        message=mssg;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
