package dat.peter.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Game_Publishers {

    @Id
    private long id;

    private String fk_app_id;

    private String fk_publisher_name;


}
