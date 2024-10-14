package hu.cubix.hr.service;

import hu.cubix.hr.model.Employee;
import hu.cubix.hr.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public abstract class EmployeeService implements EmployeePayRaiseService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Transactional
    public Employee create(Employee employee) {
        if (findById(employee.getId()) != null) {
            return null;
        }
        return save(employee);
    }

    @Transactional
    public Employee update(Employee employee) {
        if (findById(employee.getId()) == null) {
            return null;
        }
        return save(employee);
    }

    private Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee findById(final long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public List<Employee> findByJob(String job) {
        return employeeRepository.findEmployeesByJobContainsIgnoreCase(job);
    }

    public List<Employee> findByNamePrefix(String namePrefix) {
        return employeeRepository.findEmployeesByNameStartingWithIgnoreCase(namePrefix);
    }

    public List<Employee> findByWorkingBetweenDates(LocalDate startDate, LocalDate endDate) {
        return employeeRepository.findEmployeesByWorkingSinceBetween(startDate, endDate);
    }

    @Transactional
    public void delete(final long id) {
        employeeRepository.deleteById(id);
    }

    public void deleteAll() {
        employeeRepository.deleteAll();
    }
}
