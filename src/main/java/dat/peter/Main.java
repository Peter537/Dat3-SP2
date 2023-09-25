package dat.peter;

import dat.peter.config.HibernateConfig;
import dat.peter.model.Game;
import dat.peter.util.Persister;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class Main {

    public static void main(String[] args) {

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("SteamDB");


        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.getTransaction().commit();
        em.close();

        Persister persister = new Persister();
        persister.persist();
    }
}