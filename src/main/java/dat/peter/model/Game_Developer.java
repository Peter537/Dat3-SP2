package dat.peter.model;

import jakarta.persistence.*;

@Entity
public class Game_Developer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Game fk_app_id;

    @ManyToOne
    private Developer fk_developer_name;

    public Game_Developer(Game fk_app_id, Developer fk_developer_name) {
        this.fk_app_id = fk_app_id;
        this.fk_developer_name = fk_developer_name;
    }
}
