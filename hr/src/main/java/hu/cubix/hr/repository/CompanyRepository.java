package hu.cubix.hr.repository;

import hu.cubix.hr.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    List<Company> findByEmployeesSalaryGreaterThan(final int salary);

    @Query("SELECT c FROM Company c WHERE SIZE(c.employees) > :numberOfEmployees")
    List<Company> findCompaniesByEmployeesCountGreaterThan(@Param("numberOfEmployees") int numberOfEmployees);

    @Query("SELECT e.position.name, AVG(e.salary) " +
            "FROM Employee e WHERE e.company.id = :companyId " +
            "GROUP BY e.position.name " +
            "ORDER BY AVG(e.salary) DESC")
    List<Object[]> findAverageSalariesByJobForCompany(@Param("companyId") long companyId);

    @Query("select distinct c from Company c left join fetch c.employees")
    public List<Company> findAllWithEmployees();
}
