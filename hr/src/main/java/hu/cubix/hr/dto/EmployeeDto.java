package hu.cubix.hr.dto;

import hu.cubix.hr.model.Position;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record EmployeeDto(Long id, @NotEmpty String name,
                          @Positive int salary,
                          @Past LocalDate workingSince,
                          Position position) {
    public EmployeeDto() {
        this(0L, null,  1, null, null);
    }
}
