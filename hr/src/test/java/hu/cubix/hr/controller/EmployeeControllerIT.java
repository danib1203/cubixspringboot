package hu.cubix.hr.controller;

import hu.cubix.hr.dto.EmployeeDto;
import org.junit.jupiter.api.AfterEach;
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

    //Test for POST methods

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

    //Test for PUT methods

    @Test
    void testThatEmployeeIsUpdatedWithValidInput() {
        EmployeeDto employeeDto = new EmployeeDto(1, "Jozsi", "Dev", 100000,
                LocalDate.of(2024, 1, 1));
        createEmployee(employeeDto);
        List<EmployeeDto> employeesBefore = getAllEmployees();
        EmployeeDto updatedEmployee = new EmployeeDto(1, "Jozsi", "Tester", 200000,
                LocalDate.of(2014, 1, 1));

        updateEmployee(updatedEmployee, updatedEmployee.id());
        List<EmployeeDto> employeesAfter = getAllEmployees();

        assertThat(employeesAfter.size()).isEqualTo(employeesBefore.size());
        assertThat(employeesBefore.get(0).id()).isEqualTo(employeesAfter.get(0).id());
        assertThat(employeesBefore.get(0).job()).isNotEqualTo(employeesAfter.get(0).job());
    }

    @Test
    void testThatEmployeeIsNotUpdatedWithInvalidInput() {
        EmployeeDto employeeDto = new EmployeeDto(1, "Jozsi", "Dev", 100000,
                LocalDate.of(2024, 1, 1));
        createEmployee(employeeDto);
        List<EmployeeDto> employeesBefore = getAllEmployees();
        EmployeeDto invalidUpdatedEmployee = new EmployeeDto(22, "Jozsi", "Team lead", 250000,
                LocalDate.of(2024, 1, 1));

        HttpStatusCode status = getStatusOfUpdateOfEmployee(invalidUpdatedEmployee,
                invalidUpdatedEmployee.id());
        List<EmployeeDto> employeesAfter = getAllEmployees();

        assertThat(status).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(employeesBefore.get(0)).isEqualTo(employeesAfter.get(0));
    }


    @AfterEach
    public void clearEmployees(){
        webTestClient.delete().uri("/api/employees/deleteAll").exchange().expectStatus().isOk();
    }

    private List<EmployeeDto> getAllEmployees() {
        List<EmployeeDto> employees =
                webTestClient.get().uri("api/employees").exchange().expectStatus().isOk().expectBodyList(EmployeeDto.class).returnResult().getResponseBody();
        Collections.sort(employees, Comparator.comparing(EmployeeDto::id));
        return employees;
    }

    private void createEmployee(EmployeeDto newEmployee) {
        webTestClient.post().uri("/api/employees").bodyValue(newEmployee).exchange().expectStatus().isOk();
    }

    private void updateEmployee(EmployeeDto employeeToUpdate, long id) {
        webTestClient.put()
                .uri("/api/employees/{id}", id)
                .bodyValue(employeeToUpdate).exchange().expectStatus().isOk();
    }


    private HttpStatusCode getStatusOfCreationOfEmployee(EmployeeDto newEmployee) {
        return webTestClient.post()
                .uri("/api/employees")
                .bodyValue(newEmployee)
                .exchange()
                .returnResult(EmployeeDto.class)
                .getStatus();
    }

    private HttpStatusCode getStatusOfUpdateOfEmployee(EmployeeDto newEmployee, long id) {
        return webTestClient.put()
                .uri("/api/employees/{id}", id)
                .bodyValue(newEmployee)
                .exchange()
                .returnResult(EmployeeDto.class)
                .getStatus();
    }

}
