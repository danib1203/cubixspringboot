package hu.cubix.hr.repository;

import hu.cubix.hr.model.Form;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyFormRepository extends JpaRepository<Form, Long> {
}
