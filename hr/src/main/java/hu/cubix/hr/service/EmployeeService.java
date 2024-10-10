package hu.cubix.hr.service;

import hu.cubix.hr.model.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public abstract class EmployeeService implements EmployeePayRaiseService{

    private Map<Long, Employee> employees = new HashMap<>();

    public Employee create(Employee employee) {
        if (findById(employee.getId()) != null) {
            return null;
        }
        return save(employee);
    }

    public Employee update(Employee employee) {
        if (findById(employee.getId()) == null) {
            return null;
        }
        return save(employee);
    }

    private Employee save(Employee employee) {
        employees.put(employee.getId(), employee);
        return employee;
    }

    public List<Employee> findAll() {
        return new ArrayList<>(employees.values());
    }

    public Employee findById(final long id) {
        return employees.get(id);
    }

    public void delete(final long id) {
        employees.remove(id);
    }
}
