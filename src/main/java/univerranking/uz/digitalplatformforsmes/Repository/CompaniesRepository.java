package univerranking.uz.digitalplatformforsmes.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import univerranking.uz.digitalplatformforsmes.Entity.Companies;

import java.util.Set;

@Repository
public interface CompaniesRepository extends JpaRepository<Companies, Long> {
    Set<Companies> findAllById(Long id);
}
