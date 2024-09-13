package univerranking.uz.digitalplatformforsmes.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import univerranking.uz.digitalplatformforsmes.Entity.DataEntries;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DataEntriesRepository extends JpaRepository<DataEntries,Long> {
    List<DataEntries> findByRecordedAtBetweenAndAndMetrics_Id(LocalDateTime localDateTime, LocalDateTime localDateTime1, Long metricId);
}
