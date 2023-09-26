package dat.dao;

import dat.config.HibernateConfig;
import dat.dao.boilerplate.dao.GameDAO;
import dat.dto.GameLatestNewsDTO;
import dat.dto.GameLatestPlayerCountDTO;
import dat.util.Persister;
import dat.util.webscraping.WebScraper;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameDAOTest {

    EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("SteamDB");
    private GameDAO gameDAO = new GameDAO(emf);

    @Test
    void getLatestNewsTest() {
        Persister persister = new Persister();
        persister.persist();

        GameLatestNewsDTO latestNewsDTO = gameDAO.getLatestNews(730);

        assertEquals("Counter-Strike: Global Offensive", latestNewsDTO.gameTitle());

    }

    @Test
    void getLatestPlayerCountTest() {
        Persister persister = new Persister();
        persister.persist();

        GameLatestPlayerCountDTO latestNewsDTO = gameDAO.getLatestPlayerCount(730);
        System.out.println(latestNewsDTO);


    }
    
}