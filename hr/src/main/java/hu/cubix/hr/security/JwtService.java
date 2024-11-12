package hu.cubix.hr.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import hu.cubix.hr.config.HrConfigProperties;
import hu.cubix.hr.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {

    private final HrConfigProperties configProperties;
    private final Algorithm algorithm;

    @Autowired
    public JwtService(HrConfigProperties configProperties) {
        this.configProperties = configProperties;
        this.algorithm = Algorithm.HMAC256(configProperties.getSecret());
    }

    @Autowired
    EmployeeUserDetailsService employeeUserDetailsService;


    public String createJwt(UserDetails userDetails, Employee employee) {
        List<Map<String, Object>> managedEmployees =
                employeeUserDetailsService.managedEmployees(employee).stream()
                        .map(managedEmployee -> {
                            Map<String, Object> employeeData = new HashMap<>();
                            employeeData.put("id", managedEmployee.getId());
                            employeeData.put("username", managedEmployee.getUsername());
                            return employeeData;
                        })
                        .toList();

        Map<String, Object> managerData = null;
        if (employee.getManager() != null) {
            managerData = new HashMap<>();
            managerData.put("id", employee.getManager().getId());
            managerData.put("username", employee.getManager().getUsername());
        }

        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withArrayClaim("auth", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toArray(String[]::new))
                .withClaim("id", employee.getId())
                .withClaim("name", employee.getName())
                .withClaim("username", employee.getUsername())
                .withClaim("managedEmployees", managedEmployees)
                .withClaim("manager", managerData)
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(configProperties.getExpiryInterval())))
                .withIssuer(configProperties.getIssuer())
                .sign(algorithm);
    }


    public UserDetails parseJwt(String jwtToken) {
        DecodedJWT decodedJWT = JWT.require(algorithm)
                .withIssuer(configProperties.getIssuer()).build().verify(jwtToken);


        String username = decodedJWT.getSubject();
        List<SimpleGrantedAuthority> authorities = decodedJWT.getClaim("auth")
                .asList(String.class).stream().map(SimpleGrantedAuthority::new).toList();

        return new User(username, "dummy", authorities);
    }


}
