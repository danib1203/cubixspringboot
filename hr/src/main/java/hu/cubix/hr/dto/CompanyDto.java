package hu.cubix.hr.dto;


import hu.cubix.hr.model.Form;

import java.util.List;

public record CompanyDto(Long id, long registrationNumber, String name, String address,
                         List<EmployeeDto> employees, Form form) {

}
