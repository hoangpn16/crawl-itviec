package crawlitviec.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "job_model")
@Getter
@Setter
public class JobModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idjob")
    private Integer idjob;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name= "company_logo",columnDefinition = "TEXT")
    private String companyLogo;

    @Column(name = "company_address")
    private String companyAddress;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "link_job")
    private String linkJob;

    @Column(name = "type")
    private String type;
}
