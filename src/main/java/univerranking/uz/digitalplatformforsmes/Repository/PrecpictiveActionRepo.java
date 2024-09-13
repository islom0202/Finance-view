package univerranking.uz.digitalplatformforsmes.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import univerranking.uz.digitalplatformforsmes.Entity.PrescriptiveAction;

@Repository
public interface PrecpictiveActionRepo extends JpaRepository<PrescriptiveAction, Long> {
}
