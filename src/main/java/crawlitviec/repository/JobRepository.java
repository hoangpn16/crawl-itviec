package crawlitviec.repository;

import crawlitviec.repository.entity.JobModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<JobModel,Integer> {
}
