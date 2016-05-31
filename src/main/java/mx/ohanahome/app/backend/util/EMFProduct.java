package mx.ohanahome.app.backend.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by ever on 2/05/16.
 */
public final class EMFProduct {
    private static final EntityManagerFactory emfInstance =
            Persistence.createEntityManagerFactory("product");

    private EMFProduct() {}

    public static EntityManagerFactory get() {
        return emfInstance;
    }
}
