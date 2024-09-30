package hu.cubix.hr.controller;

import hu.cubix.hr.dto.CompanyDto;

import hu.cubix.hr.dto.EmployeeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final HashMap<Long, CompanyDto> companies = new HashMap<>();

    {
        companies.put(1L, new CompanyDto(1, 1234, "Test Company", "EU"));
        companies.put(2L, new CompanyDto(2, 5678, "Dev Company", "USA"));
    }


    @GetMapping()
    public List<CompanyDto> getCompanies(@RequestParam Optional<Boolean> full) {
        if (!full.orElse(false)) {
            new ArrayList<>(companies.values());
            return companies.values().stream()
                    .map(dto -> new CompanyDto(dto.getId(), dto.getRegistrationNumber(),
                            dto.getName(),
                            dto.getAddress()))
                    .collect(Collectors.toList());

        } else return new ArrayList<>(companies.values());

    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> getCompanyById(@PathVariable final long id,
                                                     @RequestParam Optional<Boolean> full) {
        if (!companies.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        CompanyDto companyDto = companies.get(id);
        if (!full.orElse(false)) {
            return ResponseEntity.ok(new CompanyDto(companyDto.getId(),
                    companyDto.getRegistrationNumber(),
                    companyDto.getName(), companyDto.getAddress()));
        } else return ResponseEntity.ok(companyDto);
    }

    @PostMapping
    public ResponseEntity<CompanyDto> createCompany(@RequestBody CompanyDto companyDto) {
        if (companies.containsKey(companyDto.getId())) {
            return ResponseEntity.badRequest().build();
        }
        companies.put(companyDto.getId(), companyDto);
        return ResponseEntity.ok(companyDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyDto> updateCompany(@PathVariable long id,
                                                    @RequestBody CompanyDto companyDto) {
        companyDto.setId(id);
        if (!companies.containsKey(id)) {
            return ResponseEntity.notFound().build();
        } else {
            companies.put(id, companyDto);
            return ResponseEntity.ok(companyDto);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable long id) {
        companies.remove(id);
    }

    @PostMapping("/addEmployee")
    public ResponseEntity<CompanyDto> addEmployeeToCompany(@RequestBody EmployeeDto employeeDto,
                                                           @RequestParam final Long companyId) {
        if (!companies.containsKey(companyId)) {
            return ResponseEntity.notFound().build();
        }
        companies.get(companyId).getEmployees().add(employeeDto);
        return ResponseEntity.ok(companies.get(companyId));
    }

    @DeleteMapping("/deleteEmployee")
    public ResponseEntity<CompanyDto> deleteEmployeeFromCompany(@RequestParam final Long employeeId,
                                                                @RequestParam final Long companyId) {
        if (!companies.containsKey(companyId)) {
            return ResponseEntity.notFound().build();
        }
        List<EmployeeDto> employees = companies.get(companyId).getEmployees();
        employees.stream().filter(e -> e.getId() == employeeId).findFirst().ifPresent(employees::remove);
        return ResponseEntity.ok(companies.get(companyId));
    }

    @PutMapping("/replaceEmployees")
    public ResponseEntity<CompanyDto> replaceEmployees(@RequestBody List<EmployeeDto> employeeDtos, @RequestParam final Long companyId) {
        if (!companies.containsKey(companyId)) {
            return ResponseEntity.notFound().build();
        }
        companies.get(companyId).setEmployees(employeeDtos);
        return ResponseEntity.ok(companies.get(companyId));
    }
}
