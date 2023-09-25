package dat.peter.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SteamAPI {

    private static final String API_URL = "https://api.steampowered.com/ISteamNews/GetNewsForApp/v2/?count=1&appid=";

    public SteamCall call(long appid) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL + appid))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String responseBody = response.body();

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(responseBody);
                JsonNode newsItems = jsonNode.get("appnews").get("newsitems").get(0);
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
