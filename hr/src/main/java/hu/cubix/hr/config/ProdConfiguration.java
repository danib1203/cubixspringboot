package hu.cubix.hr.config;

import hu.cubix.hr.service.EmployeeService;
import hu.cubix.hr.service.SmartEmployeeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@Profile("smart")
public class ProdConfiguration {

    @Bean
    public EmployeeService employeeService() {
        return new SmartEmployeeService();
    }
}
