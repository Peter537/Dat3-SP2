package dat.peter.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Game {

    @Id
    private long app_id;

    private String title;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime release_date;

    private long last_change_number;

    //Annotate the below object with the temporal annotation to specify the type of the date
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime last_record_update;

    private long all_time_peak;

    private byte[] logo;

    @ManyToOne
    private App_Type type;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "fk_app_id")
    private Set<Game_Developer> developers = new HashSet<>();

    @OneToMany(mappedBy = "fk_app_id")
    private Set<Game_System> systems = new HashSet<>();

    @OneToMany(mappedBy = "fk_app_id")
    private Set<Game_Publishers> publishers = new HashSet<>();

    @OneToMany(mappedBy = "fk_app_id")
    private Set<News> news = new HashSet<>();


    public void addDeveloper(String developer) {
        // TODO:
    }

    public void addPublisher(String publisher) {
        // TODO:
    }

    public void setType(String type) {
        // TODO:
    }

    public void addSystem(String system) {
        // TODO:
    }

    public void addScrape(Scrape scrape) {
        // TODO:
    }
}
