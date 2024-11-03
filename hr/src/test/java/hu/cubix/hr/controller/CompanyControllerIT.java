package hu.cubix.hr.controller;

import hu.cubix.hr.dto.CompanyDto;
import hu.cubix.hr.dto.EmployeeDto;
import hu.cubix.hr.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class CompanyControllerIT {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    void testThatEmployeeIsAddedToCompany() {
        CompanyDto companyDto = new CompanyDto(0L, 12345, "Test Company", "Test Address",
                new ArrayList<>(), null);
        createCompany(companyDto);
        List<CompanyDto> companiesBefore = getAllCompanies(true);
        long companyId = companiesBefore.get(companiesBefore.size() - 1).id();
        List<EmployeeDto> employeesBefore = companiesBefore.get(0).employees();

        EmployeeDto testEmployee = new EmployeeDto(null, "David", null, 200000, LocalDate.of(2020
                , 1, 1), null);
        addEmployee(testEmployee, companyId);
        List<CompanyDto> companiesAfter = getAllCompanies(true);
        List<EmployeeDto> employeesAfter = companiesAfter.get(0).employees();


        assertThat(companiesBefore).hasSize(1);
        assertThat(companiesBefore.get(0).id()).isEqualTo(companyId);
        assertThat(employeesBefore).isEmpty();
        assertThat(employeesAfter).hasSize(1);
        assertThat(employeesAfter.get(0))
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(testEmployee);
    }

    @Test
    void testThatEmployeeIsRemovedFromCompany() {
        CompanyDto companyDto = new CompanyDto(1L, 12345, "Test Company", "Test Address",
                new ArrayList<>(), null);
        createCompany(companyDto);
        List<CompanyDto> companiesBefore = getAllCompanies(true);
        long companyId = companiesBefore.get(companiesBefore.size() - 1).id();
        EmployeeDto testEmployee = new EmployeeDto(null, "David", null, 200000, LocalDate.of(2020
                , 1, 1), null);
        addEmployee(testEmployee, companyId);

        List<CompanyDto> companiesAfterAdding = getAllCompanies(true);
        List<EmployeeDto> employeesAfterAdding = companiesAfterAdding.get(0).employees();
        long employeeId = employeesAfterAdding.get(0).id();

        removeEmployee(companyId, employeeId);

        List<CompanyDto> companiesAfterRemoving = getAllCompanies(true);
        List<EmployeeDto> employeesAfterRemoving = companiesAfterRemoving.get(0).employees();
        System.out.println("Employees after removal: " + employeesAfterRemoving);

        assertThat(employeesAfterAdding).hasSize(1);
        assertThat(employeesAfterRemoving).isEmpty();

    }

    @Test
    void testThatEmployeesAreReplacedInCompany() {
        CompanyDto companyDto = new CompanyDto(1L, 12345, "Test Company", "Test Address",
                new ArrayList<>(), null);
        createCompany(companyDto);
        List<CompanyDto> companiesBeforeAddingEmployee = getAllCompanies(true);
        long companyId =
                companiesBeforeAddingEmployee.get(companiesBeforeAddingEmployee.size() - 1).id();
        EmployeeDto employeeDto = new EmployeeDto(null, "Bela", null, 120000, LocalDate.of(2020,
                1, 1), null);
        addEmployee(employeeDto, companyId);
        List<CompanyDto> companiesBefore = getAllCompanies(true);
        List<EmployeeDto> employeesBefore =
                companiesBefore.get(companiesBefore.size() - 1).employees();

        List<EmployeeDto> newEmployees = List.of(
                new EmployeeDto(null, "Bob", null, 150000, LocalDate.of(2021, 3, 10),null),
                new EmployeeDto(null, "Charlie", null, 90000, LocalDate.of(2022, 7, 20),null)
        );
        replaceEmployees(newEmployees, companyId);
        List<CompanyDto> companiesAfter = getAllCompanies(true);
        List<EmployeeDto> employeesAfter =
                companiesAfter.get(companiesAfter.size() - 1).employees();

        assertThat(employeesBefore).hasSize(1);
        assertThat(employeesBefore.get(0).name()).isEqualTo("Bela");
        assertThat(employeesAfter).hasSize(2);
        assertThat(employeesAfter.get(0))
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(newEmployees.get(0));
        assertThat(employeesAfter.get(1))
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(newEmployees.get(1));
    }


    private List<CompanyDto> getAllCompanies(boolean full) {
        return webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/companies")
                        .queryParam("full", full)
                        .build())
                .exchange()
                .expectStatus().isOk().expectBodyList(CompanyDto.class).returnResult().getResponseBody();
    }

    private void createCompany(CompanyDto company) {
        webTestClient.post().uri("/api/companies").bodyValue(company).exchange().expectStatus().isOk();
    }

    private void addEmployee(EmployeeDto employee, long companyId) {
        webTestClient.post()
                .uri(u -> u.path("/api/companies/addEmployee")
                        .queryParam("companyId", companyId)
                        .build())
                .bodyValue(employee)
                .exchange()
                .expectStatus().isOk();
    }

    private void removeEmployee(long companyId, long employeeId) {
        webTestClient.delete()
                .uri(u -> u.path("/api/companies/deleteEmployee")
                        .queryParam("companyId", companyId)
                        .queryParam("employeeId", employeeId)
                        .build())
                .exchange()
                .expectStatus().isOk();
    }

    private void replaceEmployees(List<EmployeeDto> employeeDtos, long companyId) {
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder.path("/api/companies/replaceEmployees")
                        .queryParam("companyId", companyId)
                        .build())
                .bodyValue(employeeDtos)
                .exchange()
                .expectStatus().isOk();
    }


    @BeforeEach
    public void cleanUp() {
        companyRepository.deleteAll();
    }
}
