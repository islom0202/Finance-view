package univerranking.uz.digitalplatformforsmes.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import univerranking.uz.digitalplatformforsmes.Entity.DepictiveStatus;

@Repository
public interface DepictiveRepo extends JpaRepository<DepictiveStatus, Long> {
}
