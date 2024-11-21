package hu.cubix.hr.service;

import hu.cubix.hr.model.Employee;
import hu.cubix.hr.model.Timeoff;
import hu.cubix.hr.repository.EmployeeRepository;
import hu.cubix.hr.repository.TimeoffRepository;
import hu.cubix.hr.security.EmployeeUserDetailsService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import static hu.cubix.hr.service.TimeoffSpecifications.*;


@Service
public class TimeoffService {
    @Autowired
    private TimeoffRepository timeoffRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    EmployeeUserDetailsService employeeUserDetailsService;

    @Transactional
    public Timeoff create(Timeoff timeoff, long employeeId) {
        timeoff.setRequestBy(employeeRepository.findById(employeeId).orElse(null));
        if (findById(timeoff.getId()) != null) {
            return null;
        }
        if (timeoff.getRequestDate() == null) {
            timeoff.setRequestDate(LocalDateTime.now());
        }
        if (timeoff.getAccepted() == null) {
            timeoff.setAccepted(Timeoff.AcceptStatus.PENDING);
        }
        return save(timeoff);
    }

    @Transactional
    public Timeoff update(Timeoff timeoff) {
        if (findById(timeoff.getId()) == null || !modifiable(timeoff)) {
            return null;
        }
        return save(timeoff);
    }

    private Timeoff save(Timeoff timeoff) {
        return timeoffRepository.save(timeoff);
    }

    @Transactional
    public List<Timeoff> findAll() {
        return timeoffRepository.findAll();
    }

    public Timeoff findById(final long id) {
        return timeoffRepository.findById(id).orElse(null);
    }

    @Transactional
    public Timeoff decideTimeoff(boolean accept, long timeoffId) {
        Timeoff timeoff = findById(timeoffId);
        if (timeoff == null) {
            return null;
        }
        Employee employee =
                employeeRepository.findById(timeoff.getRequestBy().getId()).orElse(null);
        Employee currentEmployee = employeeUserDetailsService.getAuthenticatedEmployee();
        boolean isCurrentEmployeeManagerOfTimeoffRequestEmployee =
                (Objects.requireNonNull(employee).getManager()).equals(currentEmployee);
        if (!isCurrentEmployeeManagerOfTimeoffRequestEmployee || !timeoff.getRequestBy().getCompany().equals(employee.getCompany())) {
            return null;
        }

        if (accept) {
            timeoff.setAccepted(Timeoff.AcceptStatus.ACCEPTED);
        } else timeoff.setAccepted(Timeoff.AcceptStatus.DECLINED);
        timeoff.setAcceptedBy(currentEmployee);
        return save(timeoff);
    }

    @Transactional
    public void delete(long id) {
        Timeoff timeoff = findById(id);
        if (modifiable(timeoff)) {
            timeoffRepository.delete(timeoff);
        }

    }

    private boolean modifiable(Timeoff timeoff) {
        Employee currentEmployee = employeeUserDetailsService.getAuthenticatedEmployee();
        boolean isCurrentEmployeeTheOriginal =
                timeoff.getRequestBy().getId().equals(currentEmployee.getId());
        boolean isAlreadyAccepted = timeoff.getAccepted() == Timeoff.AcceptStatus.PENDING;
        return isAlreadyAccepted && isCurrentEmployeeTheOriginal;
    }

    @Transactional
    public Page<Timeoff> findTimeoffsByExample(Timeoff timeoff, LocalDate timeoffStartDate,
                                               LocalDate timeoffEndDate,
                                               LocalDate creationStartDate,
                                               LocalDate creationEndDate, Pageable pageable) {

        String status = timeoff.getAccepted() != null ? timeoff.getAccepted().toString() : null;
        String requestNamePrefix = timeoff.getRequestBy() != null ?
                timeoff.getRequestBy().getName() : null;
        String acceptNamePrefix = timeoff.getAcceptedBy() != null ?
                timeoff.getAcceptedBy().getName() : null;
        LocalDateTime creationStartDateTime = creationStartDate != null ?
                creationStartDate.atStartOfDay() : null;
        LocalDateTime creationEndDateTime = creationEndDate != null ?
                creationEndDate.atTime(LocalTime.MAX) : null;


        Specification<Timeoff> specs = Specification.where(null);

        if (StringUtils.hasLength(status)) {
            specs = specs.and(statusMatch(status));
        }
        if (StringUtils.hasLength(requestNamePrefix)) {
            specs = specs.and(requestedByNamePrefix(requestNamePrefix));
        }
        if (StringUtils.hasLength(acceptNamePrefix)) {
            specs = specs.and(acceptedByNamePrefix(acceptNamePrefix));
        }

        if (timeoffStartDate != null && timeoffEndDate != null) {
            specs = specs.and(timeoffInterval(timeoffStartDate, timeoffEndDate));
        }
        if (creationStartDate != null && creationEndDate != null) {
            specs = specs.and(creationDateBetween(creationStartDateTime, creationEndDateTime));
        }

        return timeoffRepository.findAll(specs, pageable);
    }

}
