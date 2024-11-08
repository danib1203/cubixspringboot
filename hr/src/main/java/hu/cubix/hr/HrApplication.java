package hu.cubix.hr;

import hu.cubix.hr.model.Employee;
import hu.cubix.hr.model.Position;
import hu.cubix.hr.service.InitDbService;
import hu.cubix.hr.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class HrApplication implements CommandLineRunner {
    @Autowired
    SalaryService salaryService;

    @Autowired
    InitDbService initDbService;


    public static void main(String[] args) {
        SpringApplication.run(HrApplication.class, args);

    }

    @Override
    public void run(String... args) {
        Position position = new Position("IT worker", Position.Qualification.HIGH_SCHOOL, 100000);
        Employee employee1 = new Employee("Istvan", 100000, LocalDate.of(2014, 9, 21), position);
        Employee employee2 = new Employee("Bela", 100000, LocalDate.of(2013, 9, 11), position);
        salaryService.givePayRaise(employee1);
        salaryService.givePayRaise(employee2);

        initDbService.clearDB();
        initDbService.insertTestData();


    }
}
