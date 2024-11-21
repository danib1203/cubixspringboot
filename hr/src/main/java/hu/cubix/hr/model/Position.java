package hu.cubix.hr.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@Entity
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Qualification qualification;
    private int minSalary;

    public Position(String name, Qualification qualification, int minSalary) {
        this.name = name;
        this.qualification = qualification;
        this.minSalary = minSalary;
    }

    public Position() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return minSalary == position.minSalary && Objects.equals(id, position.id) && Objects.equals(name, position.name) && qualification == position.qualification;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public enum Qualification {NONE, HIGH_SCHOOL, COLLEGE, UNIVERSITY, PHD}
}
