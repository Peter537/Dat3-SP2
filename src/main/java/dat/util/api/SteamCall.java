package dat.util.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class SteamCall {
    @JsonProperty("gid")
    private long gid;
    @JsonProperty("title")
    private String title;
    @JsonProperty("url")
    private String url;
    @JsonProperty("author")
    private String author;
    @JsonProperty("contents")
    private String contents;
    @JsonProperty("date")
    private long date;
    private LocalDateTime dateAsLocalDateTime;
    @JsonProperty("appid")
    private long app_id;
    @JsonProperty("feedlabel")
    private String feed_label;
    @JsonProperty("feedname")
    private String feed_name;
    @JsonProperty("feed_type")
    private int feed_type;


    @JsonProperty("is_external_url")
    private boolean is_external_url; // This is not used, but it is needed for deserialization

    public SteamCall getSteamCall() {
        dateAsLocalDateTime = LocalDateTime.ofEpochSecond(date, 0, ZoneOffset.of("+0"));
        return this;
    }
}