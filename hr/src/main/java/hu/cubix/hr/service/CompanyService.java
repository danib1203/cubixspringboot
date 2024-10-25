package hu.cubix.hr.service;


import hu.cubix.hr.model.Company;
import hu.cubix.hr.model.Employee;
import hu.cubix.hr.repository.CompanyRepository;
import hu.cubix.hr.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    @Transactional
    public Company create(Company company) {
        if (findById(company.getId()) != null) {
            return null;
        }
        return save(company);
    }

    @Transactional
    public Company update(Company company) {
        if (findById(company.getId()) == null) {
            return null;
        }
        return save(company);
    }

    public List<Company> findByEmployeeSalaryGreaterThan(int salary) {
        return companyRepository.findByEmployeesSalaryGreaterThan(salary);
    }

    public List<Company> findCompaniesByEmployeesCountGreaterThan(int numberOfEmployees) {
        return companyRepository.findCompaniesByEmployeesCountGreaterThan(numberOfEmployees);
    }

  public List<Object[]> findAverageSalariesByJobForCompany(int companyId) {
      return companyRepository.findAverageSalariesByJobForCompany(companyId);
  }

    private Company save(Company company) {
        return companyRepository.save(company);
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company findById(final long id) {
        return companyRepository.findById(id).orElse(null);
    }

    @Transactional
    public void delete(final long id) {
        companyRepository.deleteById(id);
    }


    public Company addEmployee(final long companyId, final Employee employee) {
        Company company = companyRepository.findById(companyId).orElse(null);
        if (company != null) {
            company.getEmployees().add(employee);
            employee.setCompany(company);
            employeeRepository.save(employee);
            companyRepository.save(company);
        }
        return company;
    }


    public Company removeEmployee(final long companyId, final long employeeId) {
        Company company = companyRepository.findById(companyId).orElse(null);
        if (company != null) {
            List<Employee> employees = company.getEmployees();
            Optional<Employee> employeeOptional =
                    employees.stream().filter(e -> e.getId() == employeeId).findFirst();

            employeeOptional.ifPresent(employee -> {
                employees.remove(employee);
                employee.setCompany(null);
                employeeRepository.save(employee);
            });

            companyRepository.save(company);
        }
        return company;
    }


    public Company replaceEmployees(final long companyId, final List<Employee> newEmployees) {
        Company company = companyRepository.findById(companyId).orElse(null);

        if (company != null) {
            List<Employee> oldEmployees = company.getEmployees();
            if (oldEmployees != null) {
                oldEmployees.forEach(employee -> {
                    employee.setCompany(null);
                    employeeRepository.save(employee);
                });
            }

            newEmployees.forEach(employee -> {
                employee.setCompany(company);
                employeeRepository.save(employee);
            });
            company.setEmployees(newEmployees);
            companyRepository.save(company);
        }

        return company;
    }


}
