package hu.cubix.hr.dto;

import hu.cubix.hr.model.Timeoff;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public EmployeeDto getRequestBy() {
        return requestBy;
    }

    public void setRequestBy(EmployeeDto requestBy) {
        this.requestBy = requestBy;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    public Timeoff.AcceptStatus getAccepted() {
        return accepted;
    }

    public void setAccepted(Timeoff.AcceptStatus accepted) {
        this.accepted = accepted;
    }

    public EmployeeDto getAcceptedBy() {
        return acceptedBy;
    }

    public void setAcceptedBy(EmployeeDto acceptedBy) {
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
