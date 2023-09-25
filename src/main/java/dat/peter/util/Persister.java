package dat.peter.util;

import dat.peter.config.HibernateConfig;
import dat.peter.dao.boilerplate.DAO;
import dat.peter.model.Game;
import dat.peter.model.News;
import dat.peter.util.api.SteamAPI;
import dat.peter.util.api.SteamCall;
import dat.peter.util.webscraping.WebScraper;

import java.util.List;

public class Persister {
    private final DAO<News> newsDAO = new DAO<>(News.class, HibernateConfig.getEntityManagerFactoryConfig("SteamDB"));
    private final DAO<Game> gameDAO = new DAO<>(Game.class, HibernateConfig.getEntityManagerFactoryConfig("SteamDB"));
    public void persist() {
        List<Game> games = WebScraper.getData();
        System.out.println(games.size());

        for (Game game : games) {
            game = gameDAO.update(game);

            game = persistNews(game);
        }
    }

    private Game persistNews(Game game) {
        // Get news from Steam API
        SteamCall sc = SteamAPI.call(game.getApp_id());

        if (sc != null) {
            News news = newsDAO.findById(sc.getGid());
            if (news != null) {
                System.out.println("exists");
            } else {
                System.out.println("not exists");
                // Create new object
                news = new News(sc.getGid(), sc.getTitle(), sc.getUrl(), sc.getAuthor(), sc.getContents(), sc.getFeed_label(), sc.getDateAsLocalDateTime(), sc.getFeed_name(), sc.getFeed_type(), game);

                // Persist object and return attached object
                news = newsDAO.update(news);

                // Add news to game
                game.getNews().add(news);
            }

            gameDAO.update(game);
        }
        return game;
    }
}
