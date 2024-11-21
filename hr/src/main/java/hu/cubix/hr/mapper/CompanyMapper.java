package hu.cubix.hr.mapper;

import hu.cubix.hr.dto.CompanyDto;
import hu.cubix.hr.dto.EmployeeDto;
import hu.cubix.hr.model.Company;
import hu.cubix.hr.model.Employee;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    @Mapping(target = "employees", source = "company.employees", qualifiedByName =
            "mapEmployeesWithoutCompany")
    CompanyDto companyToDto(Company company);

    List<CompanyDto> companiesToDtos(List<Company> companies);

    Company dtoToCompany(CompanyDto companyDto);

    List<Company> dtosToCompanies(List<CompanyDto> companyDtos);

    @Mapping(target = "employees", ignore = true)
    @Named("WithoutEmployees")
    CompanyDto companyToDtoWithoutEmployees(Company company);

    @IterableMapping(qualifiedByName = "WithoutEmployees")
    List<CompanyDto> companiesToDtosWithoutEmployees(List<Company> companies);

    @Named("mapEmployeesWithoutCompany")
    default List<EmployeeDto> mapEmployeesWithoutCompany(List<Employee> employees) {
        return employees != null ? employees.stream()
                .map(employee -> new EmployeeDto(
                        employee.getId(),
                        employee.getName(),
                        employee.getPosition(),
                        employee.getSalary(),
                        employee.getWorkingSince(),
                        null
                ))
                .collect(Collectors.toList()) : Collections.emptyList();
    }
}
