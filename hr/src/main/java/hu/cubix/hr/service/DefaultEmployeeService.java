package hu.cubix.hr.service;

import hu.cubix.hr.model.Employee;

public class DefaultEmployeeService implements EmployeeService {

    @Override
    public int getPayRaisePercent(Employee employee) {
        return 5;
    }
}
