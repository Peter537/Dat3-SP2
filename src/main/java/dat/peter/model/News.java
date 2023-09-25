package dat.peter.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
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

    private String feed_type;

    @ManyToOne
    private Game fk_app_id;


}
