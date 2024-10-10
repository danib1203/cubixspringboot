package hu.cubix.hr.service;

import hu.cubix.hr.config.EmployeeConfigurationProperties;
import hu.cubix.hr.config.EmployeeConfigurationProperties.Employee.Smart;
import hu.cubix.hr.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.Period;
import java.util.Set;
import java.util.TreeMap;

public class SmartEmployeePayRaiseService extends EmployeeService {
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
        TreeMap<Double, Integer> limits = smartConfig.getLimits();
        Set<Double> yearLimits = limits.keySet();
        int maxValue = 0;

        for (double year : yearLimits) {
            if (year <= yearsOfWork) {
                maxValue = limits.get(year);
                System.out.println("current max value: "+maxValue);
            }
        }
        System.out.println("Given pay raise percent is: "+maxValue);
        return maxValue;
    }


}
