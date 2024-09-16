package hu.cubix.hr.service;

import hu.cubix.hr.model.Employee;

public class DefaultEmployeeService implements EmployeeService {

    @Override
    public double getPayRaisePercent(Employee employee) {
        return 5;
    }
}
