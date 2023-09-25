package dat.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@Getter
@Entity
public class System {

    @Id
    private String platform;

    @OneToMany(mappedBy = "platform")
    private Set<Game_System> games = new HashSet<>();

}
