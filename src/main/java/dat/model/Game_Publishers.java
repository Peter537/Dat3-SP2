package dat.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Game_Publishers {

    @Id
    private long id;

    @ManyToOne
    private Game fk_app_id;

    @ManyToOne
    private Publisher fk_publisher_name;


}
