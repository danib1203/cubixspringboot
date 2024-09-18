package hu.cubix.hr;

import hu.cubix.hr.model.Employee;
import hu.cubix.hr.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HrApplication implements CommandLineRunner {
    @Autowired
    SalaryService salaryService;

    public static void main(String[] args) {
        SpringApplication.run(HrApplication.class, args);

    }

    @Override
    public void run(String... args) {
        Employee employee1 = new Employee(213213, "Tester", 100000, LocalDate.of(2014, 9, 21));
        Employee employee2 = new Employee(32432, "Dev", 100000, LocalDate.of(2014, 9, 11));

        salaryService.givePayRaise(employee1);
        salaryService.givePayRaise(employee2);
    }
}
