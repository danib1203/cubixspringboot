package hu.cubix.hr.controller;

import hu.cubix.hr.dto.CompanyDto;
import hu.cubix.hr.dto.EmployeeDto;
import hu.cubix.hr.mapper.CompanyMapper;
import hu.cubix.hr.mapper.EmployeeMapper;
import hu.cubix.hr.model.Company;
import hu.cubix.hr.model.Employee;
import hu.cubix.hr.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @Autowired
    CompanyMapper companyMapper;

    @Autowired
    EmployeeMapper employeeMapper;

    @GetMapping()
    public List<CompanyDto> getCompanies(@RequestParam Optional<Boolean> full) {
        List<Company> companies = companyService.findAll();
        List<Company> companiesWithoutEmployees;
        if (!full.orElse(false)) {
            companiesWithoutEmployees = companies.stream().map(c -> new Company(c.getId(),
                    c.getRegistrationNumber(), c.getName(),
                    c.getAddress())).collect(Collectors.toList());
            return companyMapper.companiesToDtos(companiesWithoutEmployees);
        } else return companyMapper.companiesToDtos(companies);

    }

    @GetMapping("/{id}")
    public CompanyDto getCompanyById(@PathVariable final long id,
                                     @RequestParam Optional<Boolean> full) {
        Company company = companyService.findById(id);
        if (company == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found");
        }
        if (!full.orElse(false)) {
            company = new Company(company.getId(),
                    company.getRegistrationNumber(), company.getName(),
                    company.getAddress());
            return companyMapper.companyToDto(company);
        } else return companyMapper.companyToDto(company);
    }

    @PostMapping
    public CompanyDto createCompany(@RequestBody CompanyDto companyDto) {
        Company company = companyMapper.dtoToCompany(companyDto);
        Company createdCompany = companyService.create(company);
        if (createdCompany == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Company already exists");
        }
        return companyMapper.companyToDto(createdCompany);
    }

    @PutMapping("/{id}")
    public CompanyDto updateCompany(@PathVariable long id,
                                    @RequestBody CompanyDto companyDto) {
        Company company = companyMapper.dtoToCompany(companyDto);
        company.setId(id);
        company = companyService.update(company);
        if (company == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found");
        } else {
            return companyMapper.companyToDto(company);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable long id) {
        companyService.delete(id);
    }

    @PostMapping("/addEmployee")
    public CompanyDto addEmployeeToCompany(@RequestBody EmployeeDto employeeDto,
                                           @RequestParam final Long companyId) {
        Employee employee = employeeMapper.dtoToEmployee(employeeDto);
        Company company = companyService.addEmployee(companyId, employee);
        if (company == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found");
        }
        return companyMapper.companyToDto(company);
    }

    @DeleteMapping("/deleteEmployee")
    public CompanyDto deleteEmployeeFromCompany(@RequestParam final Long employeeId,
                                                @RequestParam final Long companyId) {
        Company company = companyService.removeEmployee(companyId, employeeId);
        if (company == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found");
        }
        return companyMapper.companyToDto(company);
    }

    @PutMapping("/replaceEmployees")
    public CompanyDto replaceEmployees(@RequestBody List<EmployeeDto> employeeDtos,
                                       @RequestParam final Long companyId) {
        List<Employee> employees = employeeMapper.dtosToEmployees(employeeDtos);
        Company company = companyService.replaceEmployees(companyId, employees);
        if (company == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found");
        }
        return companyMapper.companyToDto(company);
    }
}
