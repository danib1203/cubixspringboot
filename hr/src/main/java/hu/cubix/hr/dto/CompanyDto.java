package hu.cubix.hr.dto;


import hu.cubix.hr.model.Employee;

import java.util.List;

public record CompanyDto(long id, long registrationNumber, String name, String address,
                         List<Employee> employees) {
    public CompanyDto() {
        this(0, 0, null, null, null);
    }

}
