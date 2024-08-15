package univerranking.uz.digitalplatformforsmes.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import univerranking.uz.digitalplatformforsmes.Dto.DataRequest;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/data")
public class DataController {

    @PostMapping("/upload")
    public ResponseEntity<String> uploadData(@RequestParam("file") MultipartFile file) {
        // Logic to process and save the file data
        return ResponseEntity.ok("Data uploaded successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataRequest> getData(@PathVariable Long id) {
        // Logic to retrieve data by ID
        return ResponseEntity.ok(new DataRequest());
    }

    @GetMapping
    public ResponseEntity<List<DataRequest>> getAllData() {
        // Logic to retrieve all datasets
        return ResponseEntity.ok(new ArrayList<>());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteData(@PathVariable Long id) {
        // Logic to delete data by ID
        return ResponseEntity.ok("Data deleted successfully");
    }
}
