package hu.cubix.hr.dto;


import java.util.ArrayList;
import java.util.List;

public class CompanyDto {
    private long id;
    private long registrationNumber;
    private String name;
    private String address;


    private List<EmployeeDto> employees = new ArrayList<>();

    public CompanyDto() {
    }

    public CompanyDto(long id, long registrationNumber, String name, String address) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.name = name;
        this.address = address;
    }

    public List<EmployeeDto> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeDto> employees) {
        this.employees = employees;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(long registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
