package hu.cubix.hr.controller;

import hu.cubix.hr.model.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class EmployeeTLController {

    private List<Employee> employees = new ArrayList<>();

    {
        employees.add(new Employee(1, "Boss", 310000, LocalDate.of(2014, 1, 23)));
        employees.add(new Employee(2, "Tester", 120000, LocalDate.of(2014, 9, 21)));
        employees.add(new Employee(3, "Dev", 150000, LocalDate.of(2014, 9, 11)));
        employees.add(new Employee(4, "Team Leader", 200000, LocalDate.of(2014, 1, 2)));
        employees.add(new Employee(5, "Architect", 240000, LocalDate.of(2014, 7, 18)));
    }

    @GetMapping("/")
    public String home(Map<String, Object> model) {
        model.put("employees", employees);
        model.put("newEmployee", new Employee());
        System.out.println("Open homepage");
        return "index";
    }

    @PostMapping("/employee")
    public String createEmployee(Employee employee) {
        employees.add(employee);
        return "redirect:/";
    }
}
