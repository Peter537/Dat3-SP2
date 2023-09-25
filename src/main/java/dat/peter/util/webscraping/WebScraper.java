package dat.peter.util.webscraping;

import dat.peter.model.Game;
import dat.peter.model.Scrape;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WebScraper {

    private static final String BASE_PATH = "SteamDB/steamdb_info.html";
    private static final String APP_FILE_PATH = "SteamDB/#.html";

    public static void main(String[] args) {
        getData().forEach(System.out::println);
    }

    public static List<Game> getData() {
        Document document = getDocument(BASE_PATH);
        return document.select(".row > .span6 > .table-products.table-hover ")
                .stream()
                .filter(WebScraper::isMostPlayedGamesTable)
                .flatMap(table -> table.select("tbody > tr").stream())
                .map(WebScraper::createGameFromRow)
                .collect(Collectors.toList());
    }

    private static boolean isMostPlayedGamesTable(Element table) {
        return Optional.ofNullable(table.selectFirst("thead > tr > th > a"))
                .map(element -> element.text().equalsIgnoreCase("Most Played Games"))
                .orElse(false);
    }

    private static Game createGameFromRow(Element row) {
        Elements td = row.select("td");
        String appId = td.get(0).select("a").attr("href").split("/")[2];
        String name = td.get(1).text();
        String playersNowString = td.get(2).text();
        String peakTodayString = td.get(3).text();

        Game game = new Game();
        game.setApp_id(Long.parseLong(appId));
        game.setTitle(name);
        game.setAll_time_peak(Long.parseLong(peakTodayString.replace(",", "")));
        addAdditionalGameData(game, playersNowString);
        return game;
    }

    private static void addAdditionalGameData(Game game, String playersNowString) {
        Document document = getDocument(APP_FILE_PATH.replace("#", String.valueOf(game.getApp_id())));
        document.select(".header-wrapper > .container > .row.app-row > .span8 > .table.table-bordered.table-hover.table-responsive-flex > tbody > tr")
                .forEach(e -> parseGameDataElement(game, e));

        Scrape scrape = new Scrape();
        scrape.setScrape_date(LocalDateTime.now());
        scrape.setPlayer_now(Long.parseLong(playersNowString.replace(",", "")));
        scrape.setPlayers_peak_today(game.getAll_time_peak());

        String rating = document.select(".header-two-things > .header-thing.tooltipped.tooltipped-n > .header-thing-number.header-thing-good").text();
        scrape.setCurrent_rating(!rating.isEmpty() ? Double.parseDouble(rating.split(" ")[1].split("%")[0]) : 0.0);
        game.addScrape(scrape);

        String description = document.select(".header-description").text();
        game.setDescription(description);

        String logo = document.select(".js-open-screenshot-viewer > img").attr("src");
        game.setLogo(logo.getBytes());
    }

    private static void parseGameDataElement(Game game, Element element) {
        Elements elements = element.select("td");
        String key = elements.get(0).text();
        String value = elements.get(1).text();

        switch (key.toLowerCase()) {
            case "app type":
                game.setType(value);
                break;
            case "developer":
                String[] developers = value.split(", ");
                Stream.of(developers).forEach(game::addDeveloper);
                break;
            case "publisher":
                String[] publishers = value.split(", ");
                Stream.of(publishers).forEach(game::addPublisher);
                break;
            case "supported systems":
                String[] systems = value.split(" ");
                Stream.of(systems).forEach(game::addSystem);
                break;
            case "last change number":
                game.setLast_change_number(Long.parseLong(value));
                break;
            case "release date":
            case "last record update":
                parseAndSetDateTime(elements, key, game);
                break;
            default:
                break;
        }
    }

    private static void parseAndSetDateTime(Elements elements, String key, Game game) {
        String dateTimeAttr = elements.select("i > time").attr("datetime");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeAttr, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX"));

        if (key.equalsIgnoreCase("release date")) {
            game.setRelease_date(dateTime);
        } else if (key.equalsIgnoreCase("last record update")) {
            game.setLast_record_update(dateTime);
        }
    }

    private static Document getDocument(String path) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
            return Jsoup.parse(String.join("\n", lines));
            /* If we were to call the actual website, we would use this code instead:
            Document document = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                            "(KHTML, like Gecko) Chrome/94.0.4606.81 Safari/537.36")
                    .get();
            Thread.sleep(2000);
            return document;
             */
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}