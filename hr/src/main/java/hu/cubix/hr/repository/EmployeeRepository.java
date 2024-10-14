package hu.cubix.hr.repository;

import hu.cubix.hr.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findEmployeesByJobContainsIgnoreCase(String job);
    List<Employee> findEmployeesByNameStartingWithIgnoreCase(String name);
    List<Employee> findEmployeesByWorkingSinceBetween(LocalDate startDate, LocalDate endDate);
}
