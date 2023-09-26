package dat.model;


import dat.config.HibernateConfig;
import dat.dao.GameDevDAO;
import dat.dao.GamePubDAO;
import dat.dao.GameSysDAO;
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

    @Transient
    @ToString.Exclude
    private GameDevDAO game_developerDAO = new GameDevDAO(HibernateConfig.getEntityManagerFactoryConfig("SteamDB"));

    @Transient
    @ToString.Exclude
    private DAO<Publisher> publisherDAO = new DAO<>(Publisher.class, HibernateConfig.getEntityManagerFactoryConfig("SteamDB"));

    @Transient
    @ToString.Exclude
    private GamePubDAO game_publisherDAO = new GamePubDAO(HibernateConfig.getEntityManagerFactoryConfig("SteamDB"));

    @Transient
    @ToString.Exclude
    private DAO<System> systemDAO = new DAO<>(System.class, HibernateConfig.getEntityManagerFactoryConfig("SteamDB"));

    @Transient
    @ToString.Exclude
    private GameSysDAO game_systemDAO = new GameSysDAO(HibernateConfig.getEntityManagerFactoryConfig("SteamDB"));

    @Transient
    @ToString.Exclude
    private DAO<App_Type> app_typeDAO = new DAO<>(App_Type.class, HibernateConfig.getEntityManagerFactoryConfig("SteamDB"));

    @Transient
    @ToString.Exclude
    private DAO<Scrape> scrapeDAO = new DAO<>(Scrape.class, HibernateConfig.getEntityManagerFactoryConfig("SteamDB"));

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

    @OneToMany(mappedBy = "fk_app_id", cascade = CascadeType.MERGE)
    private Set<Game_Developer> developers = new HashSet<>();

    @OneToMany(mappedBy = "fk_app_id", cascade = CascadeType.MERGE)
    private Set<Game_System> systems = new HashSet<>();

    @OneToMany(mappedBy = "fk_app_id", cascade = CascadeType.MERGE)
    private Set<Game_Publishers> publishers = new HashSet<>();

    @OneToMany(mappedBy = "fk_app_id")
    private Set<News> news = new HashSet<>();

    @OneToMany(mappedBy = "fk_app_id", cascade = CascadeType.MERGE)
    private Set<Scrape> scrapes = new HashSet<>();


    public void addDeveloper(String developer) {
        if (developerDAO.findById(developer) == null) {
            Game_Developer gamedev = new Game_Developer(this, new Developer(developer));
            developers.add(gamedev);
        }
        else {
            Game_Developer dev = game_developerDAO.getByReferences(this, developerDAO.findById(developer));
            if (dev == null) {
                dev = new Game_Developer(this, developerDAO.findById(developer));
                developers.add(dev);
            }
            else {
                developers.add(dev);
            }
        }
    }

    public void addPublisher (String publisher){
        if (publisherDAO.findById(publisher) == null) {
            Game_Publishers gamepub = new Game_Publishers(this, new Publisher(publisher));
            publishers.add(gamepub);
        }
        else {
            Game_Publishers pub = game_publisherDAO.getByReferences(this, publisherDAO.findById(publisher));
            if (pub == null) {
                pub = new Game_Publishers(this, publisherDAO.findById(publisher));
                publishers.add(pub);
            }
            else {
                publishers.add(pub);
            }
        }
    }

    public void setType (String type){
        if (app_typeDAO.findById(type) == null) {
            this.type = app_typeDAO.update(new App_Type(type));
        }
        else {
            this.type = app_typeDAO.findById(type);
        }
    }

    public void addSystem (String system) {
        if (systemDAO.findById(system) == null) {
            Game_System gamesys = new Game_System(this, new System(system));
            systems.add(gamesys);
        }
        else {
            Game_System sys = game_systemDAO.getByReferences(this, systemDAO.findById(system));
            if (sys == null) {
                sys = new Game_System(this, systemDAO.findById(system));
                systems.add(sys);
            }
            else {
                systems.add(sys);
            }
        }
    }

    public void addScrape (Scrape scrape) {
        scrape.setFk_app_id(this);
        scrapes.add(scrape);
    }
}
