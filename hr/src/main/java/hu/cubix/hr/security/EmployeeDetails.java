package hu.cubix.hr.security;

import hu.cubix.hr.model.Employee;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record EmployeeDetails(Employee employee) implements UserDetails {


    public List<Map<String, Object>> managedEmployees(EmployeeUserDetailsService employeeUserDetailsService) {
        return employeeUserDetailsService.managedEmployees(employee).stream()
                .map(managedEmployee -> {
                    Map<String, Object> employeeData = new HashMap<>();
                    employeeData.put("id", managedEmployee.getId());
                    employeeData.put("username", managedEmployee.getUsername());
                    return employeeData;
                })
                .toList();
    }


    @Override
    public Employee employee() {
        return employee;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return employee.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }


    @Override
    public String getPassword() {
        return employee.getPassword();
    }

    @Override
    public String getUsername() {
        return employee.getUsername();
    }

}
