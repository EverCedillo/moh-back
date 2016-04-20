package mx.ohanahome.app.backend.util;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * Created by ever on 18/04/16.
 */
public class MOHQuery<T> {
    EntityManager manager;

    public MOHQuery(EntityManager manager) {
        this.manager = manager;
    }

    public T select(Class<T> className,String whereArgs){
        String select = "select "+ Constants.UNIVERSAL_ALIAS+" from "+className.getName()+" "+Constants.UNIVERSAL_ALIAS +" where "+whereArgs;
        TypedQuery<T> query=manager.createQuery(select,className);
        List<T> resultList= query.getResultList();
        return resultList.isEmpty()?null:resultList.get(0);
    }
}
