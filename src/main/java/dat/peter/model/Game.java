package dat.peter.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;

@Entity
public class Game {

    @Id
    private long app_id;
    private String title;
    private LocalDateTime release_date;

    private long last_change_number;

    private LocalDateTime last_record_update;

    private long all_time_peak;

    private byte[] logo;

    @Enumerated
    private App_Type type;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToMany
    private Set<Developer> developers;

    @ManyToMany
    private Set<Publisher> publishers;





}
