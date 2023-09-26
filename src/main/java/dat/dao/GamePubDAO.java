package dat.dao;

import dat.dao.boilerplate.DAO;
import dat.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class GamePubDAO extends DAO<Game_Publishers> {
    public GamePubDAO(EntityManagerFactory emf) {
        super(Game_Publishers.class, emf);
    }

    public Game_Publishers getByReferences(Game game, Publisher publisher) {
        try (EntityManager em = super.getEntityManagerFactory().createEntityManager()) {

            Game_Publishers gameDev = em.createQuery("SELECT gp FROM Game_Publishers gp WHERE gp.fk_app_id = :game AND gp.fk_publisher_name = :publisher", Game_Publishers.class)
                    .setParameter("game", game)
                    .setParameter("publisher", publisher)
                    .getSingleResult();
            em.close();

            return gameDev;
        }
        catch (Exception e) {
            return null;
        }
    }
}
