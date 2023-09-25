package dat.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long gid;

    private String title;

    private String url;

    private String author;

    @Column(columnDefinition = "TEXT")
    private String contents;

    private String feed_label;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime date;

    private String feed_name;

    private int feed_type;

    @ManyToOne
    private Game fk_app_id;


}
