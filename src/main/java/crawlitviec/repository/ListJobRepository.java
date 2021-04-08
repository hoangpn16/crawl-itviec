package crawlitviec.repository;

import crawlitviec.repository.entity.ListJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListJobRepository extends JpaRepository<ListJob,Integer> {

}
