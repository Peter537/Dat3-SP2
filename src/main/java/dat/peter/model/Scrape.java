package dat.peter.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Scrape {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime scrape_date;

    private long player_now;

    private long players_peak_today;

    private double current_rating;


    @ManyToOne
    private Game fk_app_id;
}
