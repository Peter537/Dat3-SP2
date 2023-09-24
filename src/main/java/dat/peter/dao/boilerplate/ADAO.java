package dat.peter.dao.boilerplate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.Getter;
import org.hibernate.exception.ConstraintViolationException;

import javax.lang.model.UnknownEntityException;
import java.util.List;

/**
 * This is an abstract class that is used to perform CRUD operations on any entity. It can be extended to gain access to basic CRUD operations.
 *
 * @param <T>
 */
abstract class ADAO<T> implements IDAO<T> {

    @Getter
    private final Class<T> entityClass;
    protected EntityManagerFactory emf;

    // Constructors
    public ADAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public ADAO(Class<T> entityClass, EntityManagerFactory emf) {
        this.entityClass = entityClass;
        this.emf = emf;
    }

    // Getters
    public EntityManagerFactory getEntityManagerFactory() {
        return this.emf;
    }

    // Setters
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }

    // Queries
    public T findById(Object id) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            return entityManager.find(this.entityClass, id);
        } catch (UnknownEntityException e) {
            System.out.println("Unknown entity: " + this.entityClass.getSimpleName());
            return null;
        }
    }

    public List<T> getAll() {
        try (EntityManager entityManager = emf.createEntityManager()) {
            return entityManager.createQuery("SELECT t FROM " + this.entityClass.getSimpleName() + " t", entityClass).getResultList();
        } catch (UnknownEntityException e) {
            System.out.println("Unknown entity: " + this.entityClass.getSimpleName());
            return null;
        }
    }

    // Standard CRUD operations
    public Boolean save(T t) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.persist(t);
            entityManager.getTransaction().commit();
            return true;
        } catch (ConstraintViolationException e) {
            System.out.println("Constraint violation: " + e.getMessage());
            return false;
        }
    }

    public T update(T t) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            entityManager.getTransaction().begin();
            T t1 = entityManager.merge(t);
            entityManager.getTransaction().commit();
            return t1;
        } catch (ConstraintViolationException e) {
            System.out.println("Constraint violation: " + e.getMessage());
            return null;
        }
    }

    public void delete(T t) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.merge(t)); // Merge to ensure the entity is in the managed state
            entityManager.getTransaction().commit();
        } catch (ConstraintViolationException e) {
            System.out.println("Constraint violation: " + e.getMessage());
        }
    }

    public void truncate() {
        try (EntityManager entityManager = emf.createEntityManager()) {
            entityManager.getTransaction().begin();
            String tableName = this.entityClass.getSimpleName();
            String sql = "TRUNCATE TABLE " + tableName + " RESTART IDENTITY CASCADE"; // CASCADE drops dependent objects
            entityManager.createNativeQuery(sql).executeUpdate();
            entityManager.getTransaction().commit();

            // Restart sequence if it exists
            try {
                entityManager.getTransaction().begin();
                sql = "ALTER SEQUENCE " + tableName + "_id_seq RESTART WITH 1";
                entityManager.createNativeQuery(sql).executeUpdate();
                entityManager.getTransaction().commit();
            } catch (Exception e) {
                System.out.println("Sequence does not exist: " + tableName + "_id_seq");
            }
        } catch (ConstraintViolationException e) {
            System.out.println("Constraint violation: " + e.getMessage());
        }
    }
}