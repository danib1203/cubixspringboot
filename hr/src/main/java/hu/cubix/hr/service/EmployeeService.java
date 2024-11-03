package hu.cubix.hr.service;

import hu.cubix.hr.model.Employee;
import hu.cubix.hr.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;

import static hu.cubix.hr.service.EmployeeSpecifications.*;

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

    @Transactional
    public List<Employee> findAll() {
       // return employeeRepository.findAllWithCompanyAndEmployees();
            List<Employee> employees = employeeRepository.findAllWithCompanyAndEmployees();
            System.out.println("Retrieved employees: " + employees);
            return employees;
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
    public List<Employee> findEmployeesByExample(Employee employee) {
        long id = employee.getId();
        String namePrefix = employee.getName();
        String positionName = employee.getPosition().getName();
        int salary = employee.getSalary();
        LocalDate entryDate = employee.getWorkingSince();
        String companyNamePrefix = employee.getCompany().getName();

        Specification<Employee> specs = Specification.where(null);

        if (id > 0) {
            specs = specs.and(hasId(id));
        }
        if (StringUtils.hasLength(namePrefix)) {
            specs = specs.and(nameStartsWith(namePrefix));
        }
        if (StringUtils.hasLength(employee.getPosition().getName())) {
            specs = specs.and(positionNameIs(positionName));
        }
        if (salary > 0) {
            specs = specs.and(salaryWithinFivePercent(salary));
        }
        if (entryDate != null) {
            specs = specs.and(entryDateIs(entryDate));
        }
        if (StringUtils.hasLength(companyNamePrefix)) {
            specs = specs.and(companyNameStartsWith(companyNamePrefix));
        }

        return employeeRepository.findAll(specs);
    }


    @Transactional
    public void delete(final long id) {
        employeeRepository.deleteById(id);
    }

    public void deleteAll() {
        employeeRepository.deleteAll();
    }
}
