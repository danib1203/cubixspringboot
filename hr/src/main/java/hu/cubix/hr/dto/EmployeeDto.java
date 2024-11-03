package hu.cubix.hr.dto;

import hu.cubix.hr.model.Position;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record EmployeeDto(Long id, @NotEmpty String name,Position position,
                          @Positive int salary,
                          @Past LocalDate workingSince,
                          CompanyDto company
) {
    public EmployeeDto() {
        this(0L, null, null, 1, null, null);
    }
}
