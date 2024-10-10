package hu.cubix.hr.controller;

import hu.cubix.hr.model.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class EmployeeTLController {

    private List<Employee> employees = new ArrayList<>();

    {
        employees.add(new Employee(1, "Istvan", "Boss", 310000, LocalDate.of(2014, 1, 23)));
        employees.add(new Employee(2, "Bela", "Tester", 120000, LocalDate.of(2014, 9, 21)));
        employees.add(new Employee(3, "Kata", "Dev", 150000, LocalDate.of(2014, 9, 11)));
        employees.add(new Employee(4, "Jani", "Team Leader", 200000, LocalDate.of(2014, 1, 2)));
        employees.add(new Employee(5, "Eva", "Architect", 240000, LocalDate.of(2014, 7, 18)));
    }

    @GetMapping("/")
    public String home(Map<String, Object> model) {
        model.put("employees", employees);
        model.put("newEmployee", new Employee());
        return "index";
    }

    @PostMapping("/employee")
    public String createEmployee(Employee employee) {
        employee.setId(employees.size() + 1);
        employees.add(employee);
        return "redirect:/";
    }

    @PutMapping("/saveEmployee/{id}")
    public String saveEmployee(@PathVariable long id, Employee employee,
                               Map<String, Object> model) {
        employee.setId(id);
        employees.set((int) (id - 1), employee);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String editEmployee(Map<String, Object> model, @PathVariable long id) {
        Employee employee =
                employees.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
        model.put("employee", employee);
        return "editEmployee";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable long id) {
        Employee employee =
                employees.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
        employees.remove(employee);
        return "redirect:/";
    }
}
