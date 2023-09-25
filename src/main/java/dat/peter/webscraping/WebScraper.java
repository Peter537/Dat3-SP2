package dat.peter.webscraping;

import dat.peter.model.Game;
import dat.peter.model.Scrape;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class WebScraper {

    private static final String BASE_PATH = "SteamDB/steamdb_info.html";
    private static final String APP_FILE_PATH = "SteamDB/#.html";

    public static void main(String[] args) {
        getData().forEach(System.out::println);
    }

    public static List<Game> getData() {
        List<Game> games = new ArrayList<>();
        Document document = getDocument(BASE_PATH);
//        System.out.println(document);
        document.select(".row > .span6 > .table-products.table-hover ").forEach(table -> {
            table.select("thead > tr > th > a").forEach(thead -> {
//                System.out.println(thead.text());
                if (thead.text().equalsIgnoreCase("Most Played Games")) {
                    table.select("tbody > tr").forEach(row -> {
//                        System.out.println("========");
                        Elements td = row.select("td");
                        String appId = td.get(0).select("a").attr("href").split("/")[2];
                        String name = td.get(1).text();
                        String playersNowString = td.get(2).text();
                        String peakTodayString = td.get(3).text();
                        Game game = new Game();
                        game.setApp_id(Long.parseLong(appId));
                        game.setTitle(name);
                        game.setAll_time_peak(Long.parseLong(peakTodayString.replace(",", "")));
//                        System.out.println(game);
//                        if (appId.equalsIgnoreCase("730")) {
                        addAdditionalGameData(game, playersNowString);
//                        }
                        games.add(game);
                    });
                }
            });
        });

        return games;
    }

    private static void addAdditionalGameData(Game game, String playersNowString) {
        Document document = getDocument(APP_FILE_PATH.replace("#", String.valueOf(game.getApp_id())));
        document.select(".header-wrapper > .container > .row.app-row > .span8 > .table.table-bordered.table-hover.table-responsive-flex > tbody > tr").forEach(e -> {
            Elements elements = e.select("td");
            String key = elements.get(0).text();
            String value = elements.get(1).text();
            if (key.equalsIgnoreCase("Release Date")) {
                String a = elements.select("i > time").attr("datetime");
                LocalDateTime releaseDate = LocalDateTime.parse(a,
                        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX"));
//                System.out.println(releaseDate);
                game.setRelease_date(releaseDate);
            }

            if (key.equalsIgnoreCase("Last Record Update")) {
                String a = elements.select("i > time").attr("datetime");
                LocalDateTime lastRecordUpdate = LocalDateTime.parse(a,
                        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX"));
//                System.out.println(lastRecordUpdate);
                game.setLast_record_update(lastRecordUpdate);
            }

//            System.out.println("Key: " + key);
//            System.out.println("Value: " + value);
            switch (key.toLowerCase()) {
                case "app type":
//                    System.out.println("App Type: " + value);
                    game.setType(value);
                    break;
                case "developer":
//                    System.out.println("Developer: " + value);
                    String[] developers = value.split(", ");
                    for (String developer : developers) {
//                        System.out.println(developer);
                        game.addDeveloper(developer);
                    }
                    break;
                case "publisher":
//                    System.out.println("Publisher: " + value);
                    String[] publishers = value.split(", ");
                    for (String publisher : publishers) {
//                        System.out.println(publisher);
                        game.addPublisher(publisher);
                    }
                    break;
                case "supported systems":
//                    System.out.println("Supported Systems: " + value);
                    String[] systems = value.split(" ");
                    for (String system : systems) {
//                        System.out.println(system);
                        game.addSystem(system);
                    }
                    break;
                case "last change number":
                    game.setLast_change_number(Long.parseLong(value));
                    break;
                default:
                    break;
            }
        });

        Scrape scrape = new Scrape();
        scrape.setScrape_date(LocalDateTime.now());
        scrape.setPlayer_now(Long.parseLong(playersNowString.replace(",", "")));
        scrape.setPlayers_peak_today(game.getAll_time_peak());
        String text = document.select(".header-two-things > .header-thing.tooltipped.tooltipped-n > .header-thing-number.header-thing-good").text();
        scrape.setCurrent_rating(0.0);
        if (!text.isEmpty()) {
            String rating = text.split(" ")[1].split("%")[0];
            scrape.setCurrent_rating(Double.parseDouble(rating));
        }
        game.addScrape(scrape);

        String description = document.select(".header-description").text();
        game.setDescription(description);

        String logo = document.select(".js-open-screenshot-viewer > img").attr("src");
        System.out.println(logo);
        game.setLogo(logo.getBytes());
    }

    private static Document getDocument(String path) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
            return Jsoup.parse(String.join("\n", lines));

//            Document document = Jsoup.connect(url)
//                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
//                            "(KHTML, like Gecko) Chrome/94.0.4606.81 Safari/537.36")
//                    .get();
//            Thread.sleep(2000);
//            return document;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}