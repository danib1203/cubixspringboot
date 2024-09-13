package hu.cubix.hr;

import hu.cubix.hr.model.Employee;
import hu.cubix.hr.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class HrApplication implements CommandLineRunner {
    @Autowired
    SalaryService salaryService;

    public static void main(String[] args) {
        SpringApplication.run(HrApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        Employee employee1 = new Employee(213213, "Tester", 50000, LocalDateTime.of(2010, 9, 21, 0, 0));
        Employee employee2 = new Employee(32432, "Dev", 65000, LocalDateTime.of(2010, 9, 11, 0, 0));
        Employee employee3 = new Employee(4564532, "Team Leader", 100000, LocalDateTime.of(2020, 5, 2, 0, 0));
        Employee employee4 = new Employee(2345, "Architect", 120000, LocalDateTime.of(2023, 7, 18, 0, 0));

        salaryService.givePayRaise(employee1);
        salaryService.givePayRaise(employee2);
        salaryService.givePayRaise(employee3);
        salaryService.givePayRaise(employee4);
    }
}
