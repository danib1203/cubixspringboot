package hu.cubix.hr.mapper;

import hu.cubix.hr.dto.EmployeeDto;
import hu.cubix.hr.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    @Mapping(target = "company.employees", ignore = true)
    EmployeeDto employeeToDto(Employee employee);

    List<EmployeeDto> employeesToDtos(List<Employee> employees);

    Employee dtoToEmployee(EmployeeDto employeeDto);

    List<Employee> dtosToEmployees(List<EmployeeDto> employeeDtos);


    default Page<EmployeeDto> employeesToDtosPage(Page<Employee> employeesPage) {
        List<EmployeeDto> employeeDtos = employeesToDtos(employeesPage.getContent());
        return new PageImpl<>(employeeDtos, employeesPage.getPageable(), employeesPage.getTotalElements());
    }
}
