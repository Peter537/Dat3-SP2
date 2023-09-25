package dat.peter.model;

import jakarta.persistence.*;

@Entity
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

private String feed_name;

private String feed_type;

@ManyToOne
private Game fk_app_id;


}
