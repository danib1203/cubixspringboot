package hu.cubix.hr.service;


import hu.cubix.hr.dto.EmployeeDto;
import hu.cubix.hr.model.Company;
import hu.cubix.hr.model.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CompanyService {

    private Map<Long, Company> companies = new HashMap<>();

    public Company create(Company company) {
        if (findById(company.getId()) != null) {
            return null;
        }
        return save(company);
    }

    public Company update(Company company) {
        if (findById(company.getId()) == null) {
            return null;
        }
        return save(company);
    }

    private Company save(Company company) {
        companies.put(company.getId(), company);
        return company;
    }

    public List<Company> findAll() {
        return new ArrayList<>(companies.values());
    }

    public Company findById(final long id) {
        return companies.get(id);
    }

    public void delete(final long id) {
        companies.remove(id);
    }

    public Company addEmployee(final long companyId, final Employee employee) {
        if (findById(companyId) == null) {
            return null;
        }
        companies.get(companyId).getEmployees().add(employee);
        return companies.get(companyId);
    }

    public Company removeEmployee(final long companyId, final long employeeId) {
        if (findById(companyId) == null) {
            return null;
        }
        List<Employee> employees = companies.get(companyId).getEmployees();
        employees.stream().filter(e -> e.getId() == employeeId).findFirst().ifPresent(employees::remove);
        return companies.get(companyId);
    }

    public Company replaceEmployees(final long companyId, final List<Employee> employees) {
        if (findById(companyId) == null) {
            return null;
        }
        companies.get(companyId).setEmployees(employees);
        return companies.get(companyId);
    }
}
