package hu.cubix.hr.controller;

import hu.cubix.hr.dto.EmployeeDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerIT {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void testThatEmployeeIsCreatedWithValidInput() {
        List<EmployeeDto> employeesBefore = getAllEmployees();
        EmployeeDto employeeDto = new EmployeeDto(1, "Jozsi", "Dev", 100000,
                LocalDate.of(2024, 1, 1));

        createEmployee(employeeDto);
        List<EmployeeDto> employeesAfter = getAllEmployees();

        assertThat(employeesAfter.subList(0, employeesBefore.size()))
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(employeesBefore);
        assertThat(employeesAfter.get(employeesAfter.size() - 1))
                .usingRecursiveComparison()
                .isEqualTo(employeeDto);
    }

    @Test
    void testEmployeeNotAddedWithInvalidInput() {
        List<EmployeeDto> employeesBefore = getAllEmployees();
        EmployeeDto invalidEmployee = new EmployeeDto(1, null, null, -1,
                null);


        HttpStatusCode status = getStatusOfCreationOfEmployee(invalidEmployee);
        List<EmployeeDto> employeesAfter = getAllEmployees();

        assertThat(status).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(employeesAfter)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(employeesBefore);
    }


    private void createEmployee(EmployeeDto newEmployee) {
        webTestClient.post().uri("/api/employees").bodyValue(newEmployee).exchange().expectStatus().isOk();
    }

    private List<EmployeeDto> getAllEmployees() {
        List<EmployeeDto> employees =
                webTestClient.get().uri("api/employees").exchange().expectStatus().isOk().expectBodyList(EmployeeDto.class).returnResult().getResponseBody();
        Collections.sort(employees, Comparator.comparing(EmployeeDto::id));
        return employees;
    }

    private HttpStatusCode getStatusOfCreationOfEmployee(EmployeeDto newEmployee) {
        return webTestClient.post()
                .uri("/api/employees")
                .bodyValue(newEmployee)
                .exchange()
                .returnResult(EmployeeDto.class)
                .getStatus();
    }

}
