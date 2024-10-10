package hu.cubix.hr.config;

import hu.cubix.hr.service.EmployeePayRaiseService;
import hu.cubix.hr.service.SmartEmployeePayRaiseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@Profile("smart")
public class ProdConfiguration {

    @Bean
    public EmployeePayRaiseService employeePayRaiseService() {
        return new SmartEmployeePayRaiseService();
    }
}
