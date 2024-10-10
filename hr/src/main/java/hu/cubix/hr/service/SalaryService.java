package hu.cubix.hr.service;


import hu.cubix.hr.model.Employee;
import org.springframework.stereotype.Service;

@Service
public class SalaryService {


    EmployeePayRaiseService employeePayRaiseService;


    public SalaryService(EmployeePayRaiseService employeePayRaiseService) {
        this.employeePayRaiseService = employeePayRaiseService;
    }


    public void givePayRaise(Employee employee) {
        double modifiedSalary = ((((double) employeePayRaiseService.getPayRaisePercent(employee) / 100) + 1.0) * employee.getSalary());
        employee.setSalary((int) modifiedSalary);
        System.out.println("Updated salary: " + employee.getSalary());
    }
}
