package dat.peter.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
public class Developer {

    @Id
    private String name;

    @OneToMany(mappedBy = "fk_developer_name")
    private Set<Game_Developer> games = new HashSet<>();
}
