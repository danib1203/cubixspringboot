package hu.cubix.hr.repository;

import hu.cubix.hr.model.Timeoff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

public interface TimeoffRepository extends JpaRepository<Timeoff, Long>,
        PagingAndSortingRepository<Timeoff, Long>, JpaSpecificationExecutor<Timeoff> {

    @Modifying
    @Transactional
    void deleteByRequestById(Long employeeId);

}
