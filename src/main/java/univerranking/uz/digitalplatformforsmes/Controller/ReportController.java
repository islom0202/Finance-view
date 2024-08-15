package univerranking.uz.digitalplatformforsmes.Controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import univerranking.uz.digitalplatformforsmes.Dto.ReportRequest;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @GetMapping("/{type}")
    public ResponseEntity<byte[]> generateReport(@PathVariable String type, @RequestParam Long dataId) {
        // Logic to generate and return a specific report
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(new byte[0]);
    }

    @PostMapping("/custom")
    public ResponseEntity<byte[]> generateCustomReport(@RequestBody ReportRequest reportRequest) {
        // Logic to generate and return a custom report
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(new byte[0]);
    }
}
