package hu.cubix.hr.security;

import hu.cubix.hr.model.Employee;
import hu.cubix.hr.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeUserDetailsService implements UserDetailsService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public EmployeeDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByUsername(username);
        if (employee == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return new EmployeeDetails(employee);
    }

    public Employee getAuthenticatedEmployee() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("No authenticated user found");
        }
        Object principal = authentication.getPrincipal();

        if (principal instanceof EmployeeDetails employeeDetails) {
            return employeeDetails.getEmployee();
        } else {
            throw new ClassCastException("Principal is not an instance of EmployeeDetails");
        }
    }

    public List<Employee> managedEmployees(Employee employee) {
        return employeeRepository.findEmployeesByManager(employee);
    }
}
