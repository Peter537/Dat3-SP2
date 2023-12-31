package dat.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
public class Publisher {

    @Id
    private String name;

    @OneToMany(mappedBy = "fk_publisher_name")
    private Set<Game_Publishers> games = new HashSet<>();

    public Publisher(String name) {
        this.name = name;
    }
}
