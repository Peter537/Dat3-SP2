package dat.peter.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * A class for making API calls to the Steam API.
 */
public class SteamAPI {

    private static final String API_URL = "https://api.steampowered.com/ISteamNews/GetNewsForApp/v2/?count=1&appid=";

    /**
     * Makes a Steam API call to retrieve news for a specific game.
     *
     * @param appid The ID of the game for which to fetch news.
     * @return A SteamCall object containing the fetched news data, or null if the call fails.
     */
    public static SteamCall call(long appid) {
        try {
            // Create an HTTP client
            HttpClient client = HttpClient.newHttpClient();

            // Create an HTTP GET request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL + appid))
                    .GET()
                    .build();

            // Send the HTTP request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Check if the request was successful (HTTP status code 200)
            if (response.statusCode() == 200) {
                String responseBody = response.body();

                // Parse the JSON response
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(responseBody);
                JsonNode newsItems = jsonNode.get("appnews").get("newsitems").get(0);

                // Deserialize the JSON into a SteamCall object
                return objectMapper.readValue(newsItems.toString(), SteamCall.class).getSteamCall();
            } else {
                System.err.println("HTTP Request failed with status code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error while making the request: " + e.getMessage());
        }
        return null;
    }
}
