package hu.cubix.hr.service;

import hu.cubix.hr.dto.EmployeeDto;
import hu.cubix.hr.model.Employee;
import hu.cubix.hr.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


    public List<Employee> findByNamePrefix(String namePrefix) {
        return employeeRepository.findEmployeesByNameStartingWithIgnoreCase(namePrefix);
    }

    public Page<Employee> findByWorkingBetweenDates(LocalDate startDate, LocalDate endDate,
                                                       Pageable pageable) {
        return employeeRepository.findEmployeesByWorkingSinceBetween(startDate, endDate, pageable);
    }

    @Transactional
    public void delete(final long id) {
        employeeRepository.deleteById(id);
    }

    public void deleteAll() {
        employeeRepository.deleteAll();
    }
}
