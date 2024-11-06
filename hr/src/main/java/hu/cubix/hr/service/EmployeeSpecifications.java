package hu.cubix.hr.service;

import hu.cubix.hr.model.Employee;
import hu.cubix.hr.model.Employee_;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class EmployeeSpecifications {

    public static Specification<Employee> hasId(long id) {
        return (root, cq, cb) -> cb.equal(root.get(Employee_.id), id);
    }

    public static Specification<Employee> nameStartsWith(String prefix) {
        return (root, cq, cb) -> cb.like(cb.lower(root.get(Employee_.name)),
                prefix.toLowerCase() + "%");
    }

    public static Specification<Employee> positionNameIs(String positionName) {
        return (root, cq, cb) -> cb.equal(root.get("position").get("name"), positionName);
    }

    public static Specification<Employee> salaryWithinFivePercent(int salary) {
        int lowerBound = (int) (salary * 0.95);
        int upperBound = (int) (salary * 1.05);
        return (root, cq, cb) -> cb.between(root.get(Employee_.salary), lowerBound, upperBound);
    }

    public static Specification<Employee> entryDateIs(LocalDate entryDate) {
        return (root, cq, cb) -> cb.equal(root.get(Employee_.workingSince), entryDate);
    }

    public static Specification<Employee> companyNameStartsWith(String prefix) {
        return (root, cq, cb) -> cb.like(cb.lower(root.get("company").get("name")),
                prefix.toLowerCase() + "%");
    }

}
