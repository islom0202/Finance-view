package univerranking.uz.digitalplatformforsmes.Controller;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import univerranking.uz.digitalplatformforsmes.Dto.DescriptiveStats;
import univerranking.uz.digitalplatformforsmes.Entity.DataEntries;
import univerranking.uz.digitalplatformforsmes.Service.DescriptiveAnalyticsService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/analytics")
@AllArgsConstructor
public class AnalyticsController {

    private final DescriptiveAnalyticsService descriptiveAnalyticsService;

    @GetMapping("/historical-data")
    public ResponseEntity<List<DataEntries>> getHistoricalData(
            @RequestParam Long metricId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<DataEntries> data = descriptiveAnalyticsService.getHistoricalData(metricId, startDate, endDate);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/descriptive-stats")
    public ResponseEntity<DescriptiveStats> getDescriptiveStats(
            @RequestParam Long metricId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<DataEntries> data = descriptiveAnalyticsService.getHistoricalData(metricId, startDate, endDate);
        DescriptiveStats stats = descriptiveAnalyticsService.calculateDescriptiveStats(data);
        return ResponseEntity.ok(stats);
    }
}
