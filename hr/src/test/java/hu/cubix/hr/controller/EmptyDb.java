package hu.cubix.hr.controller;

import hu.cubix.hr.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmptyDb {
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

    @Transactional
    public void clearDB() {
        timeoffRepository.deleteAll();
        companyRepository.deleteAll();
        employeeRepository.deleteAll();
        companyFormRepository.deleteAll();
        positionRepository.deleteAll();
    }
}
