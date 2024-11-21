package hu.cubix.hr.dto;

import hu.cubix.hr.model.Timeoff;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
public class TimeoffDto {

    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private EmployeeDto requestBy;
    private LocalDateTime requestDate;
    private Timeoff.AcceptStatus accepted;
    private EmployeeDto acceptedBy;

    public TimeoffDto() {
    }

    public TimeoffDto(Long id, LocalDate startDate, LocalDate endDate, EmployeeDto requestBy,
                      LocalDateTime requestDate, Timeoff.AcceptStatus accepted,
                      EmployeeDto acceptedBy) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.requestBy = requestBy;
        this.requestDate = requestDate;
        this.accepted = accepted;
        this.acceptedBy = acceptedBy;
    }

    @Override
    public String toString() {
        return "TimeoffDto{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", requestBy=" + requestBy +
                ", requestDate=" + requestDate +
                ", accepted=" + accepted +
                ", acceptedBy=" + acceptedBy +
                '}';
    }

}
