package fu.de180871.dao;

import fu.de180871.pojo.Employee;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

public class EmployeeDAO {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("hsf301PU");

    // ---------- CREATE (TODO 0.3) ----------
    public void save(Employee e) {
        // Truoc dong nay: e dang o trang thai NEW/TRANSIENT
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(e); // -> e chuyen sang MANAGED, se duoc INSERT khi commit
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close(); // sau dong nay, e (neu con giu tham chieu) la DETACHED
        }
    }
