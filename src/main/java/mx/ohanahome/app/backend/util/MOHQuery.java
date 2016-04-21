package mx.ohanahome.app.backend.util;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * Abandoned, in construction, failed, i don't know; todo make fix this or destroy this
 */
public class MOHQuery<T> {


    EntityManager manager;

    public MOHQuery(EntityManager manager) {
        this.manager = manager;
    }

    public T select(Class<T> className,String whereArgs){
        String select = "select "+ Constants.UNIVERSAL_ALIAS+" from "+className.getName()+" "+Constants.UNIVERSAL_ALIAS +" where "+whereArgs;

        List<T> resultList= manager.createQuery(select,className).getResultList();
        return resultList.isEmpty()?null:resultList.get(0);
    }
}
