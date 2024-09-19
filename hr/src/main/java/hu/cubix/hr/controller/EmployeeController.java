package hu.cubix.hr.controller;

import hu.cubix.hr.dto.EmployeeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final HashMap<Long, EmployeeDto> employees = new HashMap<>();

    {
        employees.put(1L, new EmployeeDto(1, "Boss", 310000,
                LocalDate.of(2014, 1, 23)));
    }

    @GetMapping
    public List<EmployeeDto> getAllEmployees() {
        return new ArrayList<>(employees.values());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable long id) {
        if (!employees.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employees.get(id));
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employee) {
        if (employees.containsKey(employee.getId())) {
            return ResponseEntity.badRequest().build();
        }
        employees.put(employee.getId(), employee);
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable long id,
                                                      @RequestBody EmployeeDto employee) {
        employee.setId(id);
        if (!employees.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        employees.put(id, employee);
        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable long id) {
        employees.remove(id);
    }

    @GetMapping("/salaryHigher")
    public ResponseEntity<List<EmployeeDto>> getEmployeesWithSalaryHigherThanParam(@RequestParam long salary) {
        List<EmployeeDto> employeesWithSalaryHigherThan =
                employees.values().stream().filter(employee -> employee.getSalary() > salary).collect(Collectors.toList());

        return ResponseEntity.ok(employeesWithSalaryHigherThan);
    }
}

