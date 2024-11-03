package hu.cubix.hr.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Employee {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int salary;
    private LocalDate workingSince;

    @ManyToOne
    private Company company;

    @ManyToOne
    private Position position;

    public Employee() {
    }

    public Employee(String name, int salary, LocalDate workingSince, Position position) {
        this.name = name;
        this.salary = salary;
        this.workingSince = workingSince;
        this.position = position;
    }

    public Employee(int salary, LocalDate workingSince) {
        this.salary = salary;
        this.workingSince = workingSince;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public LocalDate getWorkingSince() {
        return workingSince;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWorkingSince(LocalDate workingSince) {
        this.workingSince = workingSince;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", workingSince=" + workingSince +
                ", position=" + position +
                ", company=" + company +
                '}';
    }
}
