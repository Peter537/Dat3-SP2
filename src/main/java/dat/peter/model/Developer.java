package dat.peter.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@NoArgsConstructor
public class Developer {

    @Id
    private String name;

    @ManyToMany
    private Set<Game> games;
}
