package dat.dao;

import dat.dao.boilerplate.DAO;
import dat.model.Game;
import dat.model.Game_System;
import dat.model.System;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class GameSysDAO extends DAO<Game_System> {
    public GameSysDAO(EntityManagerFactory emf) {
        super(Game_System.class, emf);
    }

    public Game_System getByReferences(Game game, System system) {
        try (EntityManager em = super.getEntityManagerFactory().createEntityManager()) {

            Game_System gameDev = em.createQuery("SELECT gs FROM Game_System gs WHERE gs.fk_app_id = :game AND gs.platform = :system", Game_System.class)
                    .setParameter("game", game)
                    .setParameter("system", system)
                    .getSingleResult();
            em.close();

            return gameDev;
        }
        catch (Exception e) {
            return null;
        }
    }
}
