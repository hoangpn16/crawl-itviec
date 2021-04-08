package crawlitviec.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "list_job")
@Getter
@Setter
public class ListJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "logo")
    private String logo;

    @Column(name = "title")
    private String title;

//    @Column(name = "tag")
//    private String tag;

    @Column(name = "adress")
    private String adress;

    @Column(name = "link")
    private String link;


}
