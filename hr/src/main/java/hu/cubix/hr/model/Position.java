package hu.cubix.hr.model;

import jakarta.persistence.*;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Qualification getQualification() {
        return qualification;
    }

    public void setQualification(Qualification qualification) {
        this.qualification = qualification;
    }

    public int getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(int minSalary) {
        this.minSalary = minSalary;
    }

    public enum Qualification {NONE, HIGH_SCHOOL, COLLEGE, UNIVERSITY, PHD}
}
