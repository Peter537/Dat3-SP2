package dat.peter.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class SteamAPI {

    public SteamCall call(long appid) {
        SteamCall steamCall = null;
        try {
            // Make HTTP request
            URL url = new URL("https://api.steampowered.com/ISteamNews/GetNewsForApp/v2/?appid=" + appid + "&count=1");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            // Wait 2 seconds to avoid rate limiting or bot detection
            Thread.sleep(2000);

            // Get response
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            // Deserialize JSON response
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(content.toString());

            // Get the first news item
            JsonNode newsItems = jsonNode.get("appnews").get("newsitems").get(0);
            steamCall = objectMapper.readValue(newsItems.toString(), SteamCall.class).getSteamCall();

            in.close();
            con.disconnect();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return steamCall;
    }
}
