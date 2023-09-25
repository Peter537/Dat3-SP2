package dat.peter.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class App_Type {

    @Id
    private String type;

}
