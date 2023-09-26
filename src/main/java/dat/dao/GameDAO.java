package dat.dao;

import dat.dao.boilerplate.DAO;
import dat.dto.GameLatestNewsDTO;
import dat.dto.GameLatestPlayerCountDTO;
import dat.model.Game;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class GameDAO extends DAO<Game> {

    public GameDAO(EntityManagerFactory emf) {
        super(Game.class, emf);
    }

    public GameLatestNewsDTO getLatestNews(long appId) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT new dat.dto.GameLatestNewsDTO(g.app_id, g.title, n.title) FROM Game g JOIN g.news n WHERE g.app_id = :appId", GameLatestNewsDTO.class)
                    .setParameter("appId", appId)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public GameLatestPlayerCountDTO getLatestPlayerCount(long appId) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT new dat.dto.GameLatestPlayerCountDTO(g.app_id, g.title, s.player_now, s.players_peak_today) FROM Game g LEFT JOIN g.scrapes s WHERE g.app_id = :appId ORDER BY s.scrape_date DESC", GameLatestPlayerCountDTO.class)
                    .setParameter("appId", appId)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}