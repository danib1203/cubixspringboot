package hu.cubix.hr.controller;

import hu.cubix.hr.dto.EmployeeDto;
import hu.cubix.hr.model.Company;
import hu.cubix.hr.model.Employee;
import hu.cubix.hr.model.Position;
import hu.cubix.hr.repository.CompanyRepository;
import hu.cubix.hr.repository.EmployeeRepository;
import hu.cubix.hr.repository.PositionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeControllerIT {

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CompanyRepository companyRepository;


    @Test
    public void testFindAllWithCompanyAndEmployees() {
        List<Employee> employees = employeeRepository.findAllWithCompanyAndEmployees();
        assertThat(employees).isNotEmpty(); // Make sure there's data
        System.out.println("Employees with companies: " + employees);
    }

    //Test for POST methods

    @Test
    void testThatEmployeeIsCreatedWithValidInput() {
        Position position = new Position("Dev", Position.Qualification.NONE, 220000);
        List<EmployeeDto> employeesBefore = getAllEmployees();
        EmployeeDto employeeDto = new EmployeeDto(1L, "Jozsi", position, 100000,
                LocalDate.of(2024, 1, 1), null);

        createEmployee(employeeDto, position);
        List<EmployeeDto> employeesAfter = getAllEmployees();

        assertThat(employeesAfter.subList(0, employeesBefore.size()))
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .containsExactlyElementsOf(employeesBefore);
        assertThat(employeesAfter.get(employeesAfter.size() - 1))
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(employeeDto);
    }

    @Test
    void testEmployeeNotAddedWithInvalidInput() {
        List<EmployeeDto> employeesBefore = getAllEmployees();
        EmployeeDto invalidEmployee = new EmployeeDto(1L, null, null, -1, null, null);

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
        Position position = new Position("Dev", Position.Qualification.NONE, 220000);
        EmployeeDto employeeDto = new EmployeeDto(1L, "Jozsi", position, 100000,
                LocalDate.of(2024, 1, 1), null);
        createEmployee(employeeDto, position);
        List<EmployeeDto> employeesBefore = getAllEmployees();
        System.out.println(employeesBefore);
        EmployeeDto savedEmployee = employeesBefore.get(employeesBefore.size() - 1);
        long id = savedEmployee.id();
        EmployeeDto updatedEmployee = new EmployeeDto(1L, "Jozsi", position, 200000,
                LocalDate.of(2024, 1, 1), null);

        updateEmployee(updatedEmployee, id);
        List<EmployeeDto> employeesAfter = getAllEmployees();

        assertThat(employeesAfter.size()).isEqualTo(employeesBefore.size());
        assertThat(employeesBefore.get(0).id()).isEqualTo(employeesAfter.get(0).id());
        assertThat(employeesBefore.get(0).salary()).isNotEqualTo(employeesAfter.get(0).salary());
    }

    @Test
    void testThatEmployeeIsNotUpdatedWithInvalidInput() {
        Position position = new Position("Dev", Position.Qualification.NONE, 220000);
        EmployeeDto employeeDto = new EmployeeDto(1L, "Jozsi", position, 100000,
                LocalDate.of(2024, 1, 1), null);
        createEmployee(employeeDto, position);
        List<EmployeeDto> employeesBefore = getAllEmployees();
        EmployeeDto invalidUpdatedEmployee = new EmployeeDto(13L, "Jozsi", position,
                100000,
                LocalDate.of(2024, 1, 1), null);

        HttpStatusCode status = getStatusOfUpdateOfEmployee(invalidUpdatedEmployee,
                invalidUpdatedEmployee.id());
        List<EmployeeDto> employeesAfter = getAllEmployees();

        assertThat(status).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(employeesAfter.get(0))
                .usingRecursiveComparison()
                .isEqualTo(employeesBefore.get(0));
    }

    @BeforeEach
    public void clearEmployees() {
        webTestClient.delete().uri("/api/employees/deleteAll").exchange().expectStatus().isOk();
    }


    protected void createEmployee(EmployeeDto newEmployee, Position position) {
        positionRepository.save(position);
        webTestClient.post().uri("/api/employees")
                .bodyValue(newEmployee)
                .exchange()
                .expectStatus().isOk();
    }

    private List<EmployeeDto> getAllEmployees() {
        List<EmployeeDto> employees = webTestClient.get().uri("/api/employees")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(EmployeeDto.class)
                .returnResult()
                .getResponseBody();
        assert employees != null;
        employees.sort(Comparator.comparing(EmployeeDto::id));
        return employees;
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
