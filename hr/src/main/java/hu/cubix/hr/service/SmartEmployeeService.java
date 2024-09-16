package hu.cubix.hr.service;

import hu.cubix.hr.config.EmployeeConfigurationProperties;
import hu.cubix.hr.config.EmployeeConfigurationProperties.Employee.Smart;
import hu.cubix.hr.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.Period;

public class SmartEmployeeService implements EmployeeService {
    @Autowired
    private EmployeeConfigurationProperties config;

    @Override
    public double getPayRaisePercent(Employee employee) {
        Smart smartConfig = config.getEmployee().getSmart();
        Period period = Period.between(LocalDate.from(employee.getWorkingSince()), LocalDate.now());
        int years = period.getYears();
        int months = period.getMonths();
        int days = period.getDays();
        double monthsInYears = months / 12.0;
        int daysInMonth = LocalDate.now().lengthOfMonth();
        double daysInYears = days / (double) daysInMonth / 12.0;

        double yearsOfWork = years + monthsInYears + daysInYears;
        System.out.print("Years of working for " + employee.getJob() + ": ");
        System.out.printf("%.2f \n", yearsOfWork);
        if (yearsOfWork >= smartConfig.getTopLimit()) return smartConfig.getPercentageForTop();
        else if (yearsOfWork >= smartConfig.getMiddleLimit()) return smartConfig.getPercentageForMiddle();
        else if (yearsOfWork >= smartConfig.getLowerLimit()) return smartConfig.getPercentageForLower();
        else return smartConfig.getPercentageForRest();
    }
}
