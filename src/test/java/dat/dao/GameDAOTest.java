package dat.dao;

import dat.config.HibernateConfig;
import dat.dao.boilerplate.dao.GameDAO;
import dat.dto.GameLatestNewsDTO;
import dat.dto.GameLatestPlayerCountDTO;
import dat.util.Persister;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GameDAOTest {

    private final GameDAO gameDAO = new GameDAO(HibernateConfig.getEntityManagerFactoryConfig("SteamDB"));

    @BeforeAll
    void setUp() {
        Persister persister = new Persister();
        persister.persist();
    }

    @Test
    void getLatestNewsTest() {
        GameLatestNewsDTO latestNewsDTO = gameDAO.getLatestNews(730);
        assertEquals("Counter-Strike: Global Offensive", latestNewsDTO.gameTitle());
    }

    @Test
    void getLatestPlayerCountTest() {
        GameLatestPlayerCountDTO latestPlayerCount = gameDAO.getLatestPlayerCount(730);
        System.out.println(latestPlayerCount);
        assertEquals("Counter-Strike: Global Offensive", latestPlayerCount.gameTitle());
        assertEquals(111, latestPlayerCount.peakPlayerCount());
    }
}