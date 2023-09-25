package dat.model;


import dat.config.HibernateConfig;
import dat.dao.boilerplate.DAO;
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

    @Transient
    @ToString.Exclude
    private DAO<Developer> developerDAO = new DAO<>(Developer.class, HibernateConfig.getEntityManagerFactoryConfig("SteamDB"));

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

    @OneToMany(mappedBy = "fk_app_id")
    private Set<Scrape> scrapes = new HashSet<>();


    public void addDeveloper(String developer) {
        if (developerDAO.findById(developer) == null) {
            Game_Developer dev = new Game_Developer(this, new Developer(developer));
            developers.add(dev);
        }
    }

        public void addPublisher (String publisher){
            // TODO: check DB if the publisher exists and link to existing if it does. Else it should create a new publisher and link it
        }

        public void setType (String type){
            // TODO:
        }

        public void addSystem (String system){
            // TODO:
        }

        public void addScrape (Scrape scrape){
            // TODO:
        }
    }
