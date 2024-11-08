package hu.cubix.hr.model;

import jakarta.persistence.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Timeoff {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDate startDate;

    private LocalDate endDate;

    @ManyToOne
    private Employee requestBy;

    private LocalDateTime requestDate;

    @Enumerated(EnumType.STRING)
    private AcceptStatus accepted = AcceptStatus.PENDING;

    @ManyToOne
    private Employee acceptedBy;

    public Timeoff() {
    }

    public Timeoff(LocalDate startDate, LocalDate endDate, Employee requestBy) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.requestBy = requestBy;
        this.requestDate = LocalDateTime.now();
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

    public Employee getRequestBy() {
        return requestBy;
    }

    public void setRequestBy(Employee requestBy) {
        this.requestBy = requestBy;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    public AcceptStatus getAccepted() {
        return accepted;
    }

    public void setAccepted(AcceptStatus accepted) {
        this.accepted = accepted;
    }

    public Employee getAcceptedBy() {
        return acceptedBy;
    }

    public void setAcceptedBy(Employee acceptedBy) {
        this.acceptedBy = acceptedBy;
    }

    public enum AcceptStatus {ACCEPTED, DECLINED, PENDING}

    @Override
    public String toString() {
        return "Timeoff{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", requestBy=" + requestBy +
                ", requestDate=" + requestDate +
                ", accepted=" + accepted +
                ", acceptedBy=" + acceptedBy +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Timeoff timeoff = (Timeoff) o;
        return accepted == timeoff.accepted && Objects.equals(id, timeoff.id) && Objects.equals(startDate, timeoff.startDate) && Objects.equals(endDate, timeoff.endDate) && Objects.equals(requestBy, timeoff.requestBy) && Objects.equals(requestDate, timeoff.requestDate) && Objects.equals(acceptedBy, timeoff.acceptedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
