package fu.de180871.dao;

import fu.de180871.pojo.Department;
import fu.de180871.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

import java.util.List;

public class DepartmentDAO {
    private final EntityManagerFactory emf = JPAUtil.getEMF();
    public void save(Department department) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(department);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) {
                trans.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public List<Department> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT d FROM Department d", Department.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Department findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Department.class, id);
        } finally {
            em.close();
        }
    }

    public Department update(Department department) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction trans = em.getTransaction();
        Department updatedDepartment = null;
        try {
            trans.begin();
            // Gán lại kết quả trả về từ em.merge()
            updatedDepartment = em.merge(department);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) {
                trans.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
        return updatedDepartment;
    }

    public void delete(Long id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            Department department = em.find(Department.class, id);
            if (department != null) {
                em.remove(department);
            }
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) {
                trans.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
    public Department findByIdWithEmployees(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT d FROM Department d JOIN FETCH d.employees WHERE d.id = :id",
                            Department.class
                    )
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    public List<Department> findAllWithEmployees() {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        try {
            // Sử dụng DISTINCT để tránh trùng lặp dữ liệu Department khi JOIN với Employees
            return em.createQuery(
                    "SELECT DISTINCT d FROM Department d LEFT JOIN FETCH d.employees",
                    Department.class
            ).getResultList();
        } finally {
            em.close();
        }
    }
}