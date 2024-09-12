package hu.cubix.hr.service;


import hu.cubix.hr.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
public class SalaryService {
    @Autowired
    EmployeeService employeeService;

    public void givePayRaise(Employee employee) {
        double modifiedSalary = ((((double) employeeService.getPayRaisePercent(employee) / 100) + 1.0) * employee.getSalary());
        employee.setSalary((int) modifiedSalary);
        System.out.println(" and updated salary: " + employee.getSalary());
    }
}
