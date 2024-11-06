package hu.cubix.hr.service;

import com.github.javafaker.Faker;
import hu.cubix.hr.model.*;
import hu.cubix.hr.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.IntStream;

import static hu.cubix.hr.model.Position.Qualification;

@Service
public class InitDbService {

    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    CompanyFormRepository companyFormRepository;
    @Autowired
    PositionRepository positionRepository;
    @Autowired
    TimeoffRepository timeoffRepository;
    Random random = new Random();


    @Transactional
    public void clearDB() {
        timeoffRepository.deleteAll();
        companyRepository.deleteAll();
        employeeRepository.deleteAll();
        companyFormRepository.deleteAll();
        positionRepository.deleteAll();
    }

    @Transactional
    public void insertTestData() {
        //create forms
        Form form1 = new Form("LimitedPartnership");
        Form form2 = new Form("LLC");
        Form form3 = new Form("Corporation");

        companyFormRepository.save(form1);
        companyFormRepository.save(form2);
        companyFormRepository.save(form3);

        // create companies
        Company company1 = new Company(14321, "IT company", "BUD, Vajda Street 2.", form1);
        Company company2 = new Company(46445, "Movie company", "DEB, Fő Street 536.", form2);
        Company company3 = new Company(78594, "Book company", "BUD, Robert Karoly Street 83.",
                form3);

        companyRepository.save(company1);
        companyRepository.save(company2);
        companyRepository.save(company3);

        // create employee
        Faker faker = new Faker(new Locale("hu"));

        List<Position> positions = IntStream.rangeClosed(1, 10)
                .mapToObj(p -> new Position(faker.job().position(),
                        Arrays.stream(Qualification.values()).toList().get(random.nextInt(Qualification.values().length)),
                        faker.number().numberBetween(100000, 500000))).toList();

        List<Employee> employees = IntStream.rangeClosed(1, 30)
                .mapToObj(e -> new Employee(
                        faker.name().firstName(),
                        faker.number().numberBetween(100000, 1000000),
                        faker.date().between(Date.valueOf(LocalDate.of(2000, 1, 1)),
                                Date.valueOf(LocalDate.now())).toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                        positions.get(random.nextInt(positions.size()))
                )).toList();

        //assign employees to companies
        for (Employee employee : employees) {
            if (employee.getSalary() < employee.getPosition().getMinSalary()) {
                employee.setSalary(employee.getPosition().getMinSalary());
            }
            int rnd = random.nextInt(1, 4);
            switch (rnd) {
                case 1:
                    employee.setCompany(company1);
                    break;
                case 2:
                    employee.setCompany(company2);
                    break;
                case 3:
                    employee.setCompany(company3);
                    break;
            }
        }
        employeeRepository.saveAll(employees);
        positionRepository.saveAll(positions);

        List<Timeoff> timeoffs = new ArrayList<>();
        while (timeoffs.size() < 50) {
            LocalDate startDate = fakeTimeoffStartDateConverter();
            timeoffs.add(new Timeoff(
                    startDate,
                    fakeTimeoffEndDateConverter(startDate),
                    employees.get(random.nextInt(employees.size()))));
        }
        timeoffRepository.saveAll(timeoffs);

        // save companies again
        companyRepository.save(company1);
        companyRepository.save(company2);
        companyRepository.save(company3);
    }

    private LocalDate fakeTimeoffEndDateConverter(LocalDate startDate) {
        Faker faker = new Faker(new Locale("hu"));
        Date date = Date.valueOf(startDate);
        return faker.date().between(date,
                Date.valueOf(LocalDate.now())).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    }

    private LocalDate fakeTimeoffStartDateConverter() {
        Faker faker = new Faker(new Locale("hu"));
        return faker.date().between(Date.valueOf(LocalDate.of(2024, 1, 1)),
                Date.valueOf(LocalDate.now())).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
