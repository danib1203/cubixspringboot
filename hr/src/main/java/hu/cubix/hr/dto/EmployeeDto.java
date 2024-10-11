package hu.cubix.hr.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record EmployeeDto(long id, @NotEmpty String name, @NotEmpty String job,
                          @Positive int salary,
                          @Past LocalDate workingSince) {
    public EmployeeDto() {
        this(0, null, null, 1, null);
    }
}
