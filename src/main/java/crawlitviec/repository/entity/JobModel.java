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

    @Column(name = "company_address")
    private String companyAddress;

    @Column(name = "top_reasons")
    private String topReasons;

    @Column(name = "skill_requirement")
    private String skillRequirement;

    @Column(name = "job_description")
    private String jobDescription;

//    @Column(name = "time_updated")
//    private String timeUpdated;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "reason_choosing")
    private String reasonChoosing;

    @Column(name = "link_job")
    private String linkJob;
}
