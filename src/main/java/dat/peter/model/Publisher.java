package dat.peter.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@NoArgsConstructor
public class Publisher {

    @Id
    private String name;


    @ManyToMany
    private Set<Game> games;
}
