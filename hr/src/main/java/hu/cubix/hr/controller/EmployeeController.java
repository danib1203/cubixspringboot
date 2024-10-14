package hu.cubix.hr.controller;

import hu.cubix.hr.dto.EmployeeDto;
import hu.cubix.hr.mapper.EmployeeMapper;
import hu.cubix.hr.model.Employee;
import hu.cubix.hr.service.EmployeePayRaiseService;
import hu.cubix.hr.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    EmployeePayRaiseService employeePayRaiseService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    EmployeeMapper employeeMapper;


    @GetMapping
    public List<EmployeeDto> findAll() {
        return new ArrayList<>(employeeMapper.employeesToDtos(employeeService.findAll()));
    }

    @GetMapping("/{id}")
    public EmployeeDto getEmployeeById(@PathVariable long id) {
        Employee employee = employeeService.findById(id);
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
        }
        return employeeMapper.employeeToDto(employee);
    }

    @PostMapping
    public EmployeeDto createEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        Employee employee = employeeMapper.dtoToEmployee(employeeDto);
        Employee createdEmployee = employeeService.create(employee);
        if (createdEmployee == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return employeeMapper.employeeToDto(createdEmployee);
    }

    @PutMapping("/{id}")
    public EmployeeDto updateEmployee(@PathVariable long id,
                                      @RequestBody EmployeeDto employeeDto) {
        Employee employee = employeeMapper.dtoToEmployee(employeeDto);
        employee.setId(id);
        Employee createdEmployee = employeeService.update(employee);
        if (createdEmployee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
        }
        return employeeMapper.employeeToDto(createdEmployee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable long id) {
        employeeService.delete(id);
    }

    @DeleteMapping("/deleteAll")
    public void deleteAllEmployees() {
        employeeService.deleteAll();
    }

    @GetMapping("/salaryHigher")
    public List<EmployeeDto> getEmployeesWithSalaryHigherThanParam(@RequestParam long salary) {
        List<Employee> employees = employeeService.findAll();
        List<EmployeeDto> employeesDto = employeeMapper.employeesToDtos(employees);
        List<EmployeeDto> employeesWithSalaryHigherThan =
                employeesDto.stream().filter(employee -> employee.salary() > salary).collect(Collectors.toList());

        return employeesWithSalaryHigherThan;
    }

    @PostMapping("/getPayRaise")
    public double getEmployeePayRaisePercent(@RequestBody Employee employee) {
        return employeePayRaiseService.getPayRaisePercent(employee);
    }
}

