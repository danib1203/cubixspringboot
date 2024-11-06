package hu.cubix.hr.service;

import hu.cubix.hr.model.Employee;
import hu.cubix.hr.model.Timeoff;
import hu.cubix.hr.repository.EmployeeRepository;
import hu.cubix.hr.repository.TimeoffRepository;
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

import static hu.cubix.hr.service.TimeoffSpecifications.*;


@Service
public class TimeoffService {
    @Autowired
    private TimeoffRepository timeoffRepository;
    @Autowired
    private EmployeeRepository employeeRepository;


    @Transactional
    public Timeoff create(Timeoff timeoff) {
        if (findById(timeoff.getId()) != null) {
            return null;
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
    public Timeoff decideTimeoff(boolean accept, long timeoffId, long employeeId) {
        Timeoff timeoff = findById(timeoffId);
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (timeoff == null || employee == null) {
            return null;
        }
        if (accept) {
            timeoff.setAccepted(Timeoff.AcceptStatus.ACCEPTED);
        } else timeoff.setAccepted(Timeoff.AcceptStatus.DECLINED);
        timeoff.setAcceptedBy(employee);
        return save(timeoff);
    }

    @Transactional
    public void delete(long id) {
        Timeoff timeoff = findById(id);
        if (modifiable(timeoff)) {
            timeoffRepository.delete(timeoff);
        }

    }

    public Page<Timeoff> findAllWithPageable(Pageable pageable) {
        return timeoffRepository.findAll(pageable);
    }

    private boolean modifiable(Timeoff timeoff) {
        return timeoff.getAccepted() != null;
    }

    @Transactional
    public Page<Timeoff> findTimeoffsByExample(Timeoff timeoff, LocalDate timeoffStartDate,
                                               LocalDate timeoffEndDate,
                                               LocalDate  creationStartDate,
                                               LocalDate  creationEndDate, Pageable pageable) {

            String status = timeoff.getAccepted() != null ? timeoff.getAccepted().toString() : null;
            String requestNamePrefix = timeoff.getRequestBy() != null ?
                    timeoff.getRequestBy().getName() : null;
            String acceptNamePrefix = timeoff.getAcceptedBy() != null ?
                    timeoff.getAcceptedBy().getName() : null;
        LocalDateTime creationStartDateTime = creationStartDate != null ? creationStartDate.atStartOfDay() : null;
        LocalDateTime creationEndDateTime = creationEndDate != null ? creationEndDate.atTime(LocalTime.MAX) : null;


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
