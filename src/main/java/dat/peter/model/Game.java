package dat.peter.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
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

    @Enumerated(EnumType.STRING)
    private App_Type type;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToMany
    private Set<Developer> developers = new HashSet<>();

    @ManyToMany
    private Set<System> systems = new HashSet<>();

    @ManyToMany
    private Set<Publisher> publishers = new HashSet<>();

    @OneToMany(mappedBy = "fk_app_id")
    private Set<News> news = new HashSet<>();


}
