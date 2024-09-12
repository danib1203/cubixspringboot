package hu.cubix.hr.service;

import ch.qos.logback.core.util.Duration;
import hu.cubix.hr.model.Employee;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@Primary
public class SmartEmployeeService implements EmployeeService {
    @Override
    public int getPayRaisePercent(Employee employee) {
        long yearsOfWork = ChronoUnit.YEARS.between(employee.getWorkingSince(), LocalDateTime.now());
        System.out.print("Years of working for " + employee.getJob() + ": " + yearsOfWork);
        if (yearsOfWork >= 10) return 10;
        else if (yearsOfWork >= 5) return 5;
        else if (yearsOfWork >= 2.5) return 2;
        else return 0;
    }
}
