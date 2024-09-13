package hu.cubix.hr.service;

import hu.cubix.hr.config.EmployeeConfigurationProperties;
import hu.cubix.hr.config.EmployeeConfigurationProperties.Employee.Smart;
import hu.cubix.hr.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public class SmartEmployeeService implements EmployeeService {
    @Autowired
    private EmployeeConfigurationProperties config;

    @Override
    public int getPayRaisePercent(Employee employee) {
        Smart smartConfig = config.getEmployee().getSmart();
        Period period = Period.between(LocalDate.from(employee.getWorkingSince()), LocalDate.now());
        int yearsOfWork = period.getYears();
        System.out.println("Years of working for " + employee.getJob() + ": " + yearsOfWork);
        if (yearsOfWork >= smartConfig.getTopLimit()) return smartConfig.getPercentageForTop();
        else if (yearsOfWork >= smartConfig.getMiddleLimit()) return smartConfig.getPercentageForMiddle();
        else if (yearsOfWork >= smartConfig.getLowerLimit()) return smartConfig.getPercentageForLower();
        else return smartConfig.getPercentageForRest();
    }
}
