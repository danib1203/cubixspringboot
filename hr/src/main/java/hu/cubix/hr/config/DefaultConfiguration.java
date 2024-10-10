package hu.cubix.hr.config;

import hu.cubix.hr.service.DefaultEmployeePayRaiseService;
import hu.cubix.hr.service.EmployeePayRaiseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@Profile("!smart")
public class DefaultConfiguration {

    @Bean
    public EmployeePayRaiseService employeeService() {
        return new DefaultEmployeePayRaiseService();
    }
}
