package dat.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Game_Publishers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Game fk_app_id;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Publisher fk_publisher_name;

    public Game_Publishers(Game fk_app_id, Publisher fk_publisher_name) {
        this.fk_app_id = fk_app_id;
        this.fk_publisher_name = fk_publisher_name;
    }

}
