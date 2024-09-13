package univerranking.uz.digitalplatformforsmes.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import univerranking.uz.digitalplatformforsmes.Dto.CompanyDto;
import univerranking.uz.digitalplatformforsmes.Entity.Companies;
import univerranking.uz.digitalplatformforsmes.Repository.CompaniesRepository;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CompanyService {

    private final CompaniesRepository companiesRepository;
    public Companies save(CompanyDto dto) {
        Companies company = new Companies();
        company.setName(dto.getName());
        company.setIndustry(dto.getIndustry());
        company.setAddress(dto.getAddress());
        company.setCreatedAt(LocalDateTime.now());
        companiesRepository.save(company);
        return company;
    }
}
