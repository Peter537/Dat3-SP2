package dat.util.webscraping;

import dat.model.Game;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WebScraperTest {

    @Test
    void getData() {
        List<Game> games = WebScraper.getData();
        assertEquals(15, games.size());
        Game game = games.stream().filter(g -> g.getApp_id() == 730).findFirst().orElseThrow();
        assertEquals("Counter-Strike: Global Offensive", game.getTitle());
    }
}