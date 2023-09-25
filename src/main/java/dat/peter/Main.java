package dat.peter;

import dat.peter.config.HibernateConfig;
import dat.peter.model.Game;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class Main {

    public static void main(String[] args) {

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("SteamDB");


        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        em.close();

    }
}