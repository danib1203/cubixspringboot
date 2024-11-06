package hu.cubix.hr.repository;

import hu.cubix.hr.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long>,
        JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

    List<Employee> findEmployeesByNameStartingWithIgnoreCase(String name);

    Page<Employee> findEmployeesByWorkingSinceBetween(LocalDate startDate, LocalDate endDate,
                                                      Pageable pageable);

}
