package fu.de180871.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

    private static final EntityManagerFactory EMF =
            Persistence.createEntityManagerFactory("hsf302FU");

    // Private constructor để ngăn việc tạo instance
    private JPAUtil() {
    }

    public static EntityManagerFactory getEMF() {
        return EMF;
    }

    public static void close() {
        if (EMF != null && EMF.isOpen()) {
            EMF.close();
        }
    }
}