package dat.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Game_Developer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Game fk_app_id;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Developer fk_developer_name;

    public Game_Developer(Game fk_app_id, Developer fk_developer_name) {
        this.fk_app_id = fk_app_id;
        this.fk_developer_name = fk_developer_name;
    }
}
