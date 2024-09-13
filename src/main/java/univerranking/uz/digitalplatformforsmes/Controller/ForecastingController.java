package univerranking.uz.digitalplatformforsmes.Controller;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import univerranking.uz.digitalplatformforsmes.Service.DescriptiveAnalyticsService;
import univerranking.uz.digitalplatformforsmes.Service.PredictedSales;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/forecast")
@AllArgsConstructor
public class ForecastingController {

    private final DescriptiveAnalyticsService descriptiveAnalyticsService;

    @GetMapping
    public ResponseEntity<?> forecast(@RequestParam Long metricId,
                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) throws IOException {
        return ResponseEntity.ok(descriptiveAnalyticsService.
                forecastSalesWithPython(metricId,startDate,endDate));
    }

    @GetMapping("/recommended")
    public ResponseEntity<?> recommended(@RequestParam Long metricId,
                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) throws IOException {

        List<PredictedSales> action = descriptiveAnalyticsService.
                forecastSalesWithPython(metricId,startDate,endDate);
        return ResponseEntity.ok(descriptiveAnalyticsService
                .recommendAction(action));
    }
}
