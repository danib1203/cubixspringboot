package hu.cubix.hr.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;


@Getter
@Entity
public class Employee {

    @Setter
    @Id
    @GeneratedValue
    private Long id;
    @Setter
    private String name;
    @Setter
    private int salary;
    @Setter
    private LocalDate workingSince;
    @Setter
    private String username;
    @Setter
    private String password;
    @ManyToOne
    private Employee manager;
    @Setter
    @ManyToOne
    private Company company;


    @Setter
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "position_id")
    private Position position;

    @Setter
    @ElementCollection(fetch = FetchType.EAGER)
    Set<String> roles;

    public Employee() {
    }

    public Employee(String name, int salary, LocalDate workingSince, Position position) {
        this.name = name;
        this.salary = salary;
        this.workingSince = workingSince;
        this.position = position;
    }

    public Employee(String name, int salary, LocalDate workingSince, Position position,
                    Employee manager) {
        this.name = name;
        this.salary = salary;
        this.workingSince = workingSince;
        this.position = position;
        setManager(manager);
    }

    public Employee(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setManager(Employee manager) {
        if (this != manager) {
            this.manager = manager;
        } else {
            this.manager = null;
            throw new IllegalArgumentException("Employee cannot be their own manager");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", workingSince=" + workingSince +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", manager=" + manager +
                ", company=" + company +
                ", position=" + position +
                '}';
    }
}
