package dat.peter.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Scrape {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime scrape_date;

    private long player_now;

    private long players_peak_today;

    private double current_rating;


    @ManyToOne
    private Game fk_app_id;
}
