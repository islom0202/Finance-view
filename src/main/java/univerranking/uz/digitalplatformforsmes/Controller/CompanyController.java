package univerranking.uz.digitalplatformforsmes.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import univerranking.uz.digitalplatformforsmes.Dto.CompanyDto;
import univerranking.uz.digitalplatformforsmes.Service.CompanyService;
import univerranking.uz.digitalplatformforsmes.Utils.RestConstants;

@RestController
@RequestMapping(RestConstants.PATH+"/company")
@AllArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody CompanyDto dto){
        return ResponseEntity.ok(companyService.save(dto));
    }
}
