package dat.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class App_Type {

    @Id
    private String type;

    public App_Type(String type) {
        this.type = type;
    }
}
