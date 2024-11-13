package hu.cubix.hr.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
public class Employee {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int salary;
    private LocalDate workingSince;
    private String username;
    private String password;
    @ManyToOne
    private Employee manager;
    @ManyToOne
    private Company company;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "position_id")
    private Position position;

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

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
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

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public LocalDate getWorkingSince() {
        return workingSince;
    }

    public void setWorkingSince(LocalDate workingSince) {
        this.workingSince = workingSince;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        if (this != manager) {
            this.manager = manager;
        } else {
            this.manager = null;
            throw new IllegalArgumentException("Employee cannot be their own manager");
        }
    }


    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
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
