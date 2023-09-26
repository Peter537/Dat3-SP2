package dat.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Game_System {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Game fk_app_id;
    @ManyToOne(cascade = CascadeType.MERGE)
    private System platform;

    public Game_System(Game fk_app_id, System platform) {
        this.fk_app_id = fk_app_id;
        this.platform = platform;
    }
}
