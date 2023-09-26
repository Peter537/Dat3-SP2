package dat.dao;

import dat.dao.boilerplate.DAO;
import dat.model.Developer;
import dat.model.Game;
import dat.model.Game_Developer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class GameDevDAO extends DAO<Game_Developer> {
    public GameDevDAO(EntityManagerFactory emf) {
        super(Game_Developer.class, emf);
    }

    public Game_Developer getByReferences(Game game, Developer developer) {
        try (EntityManager em = super.getEntityManagerFactory().createEntityManager()) {

            Game_Developer gameDev = em.createQuery("SELECT gd FROM Game_Developer gd WHERE gd.fk_app_id = :game AND gd.fk_developer_name = :developer", Game_Developer.class)
                    .setParameter("game", game)
                    .setParameter("developer", developer)
                    .getSingleResult();
            em.close();

            return gameDev;
        }
        catch (Exception e) {
            return null;
        }
    }
}
