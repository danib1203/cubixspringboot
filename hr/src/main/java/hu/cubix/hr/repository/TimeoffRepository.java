package hu.cubix.hr.repository;

import hu.cubix.hr.model.Timeoff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TimeoffRepository extends JpaRepository<Timeoff, Long>,
        PagingAndSortingRepository<Timeoff, Long>, JpaSpecificationExecutor<Timeoff> {


}
