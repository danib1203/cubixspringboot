package hu.cubix.hr.service;

import hu.cubix.hr.model.Employee;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {
    double getPayRaisePercent(Employee employee);
}
