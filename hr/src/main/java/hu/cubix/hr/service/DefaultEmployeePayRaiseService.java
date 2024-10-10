package hu.cubix.hr.service;

import hu.cubix.hr.model.Employee;

public class DefaultEmployeePayRaiseService extends EmployeeService {

    @Override
    public double getPayRaisePercent(Employee employee) {
        return 5;
    }
}
