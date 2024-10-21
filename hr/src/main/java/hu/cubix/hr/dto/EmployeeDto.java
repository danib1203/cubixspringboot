package hu.cubix.hr.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record EmployeeDto(Long id, @NotEmpty String name, @NotEmpty String job,
                          @Positive int salary,
                          @Past LocalDate workingSince) {
    public EmployeeDto() {
        this(0L, null, null, 1, null);
    }
}
