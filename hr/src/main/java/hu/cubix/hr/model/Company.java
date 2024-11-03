package hu.cubix.hr.model;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@NamedEntityGraph(
        name = "Company.withFormAndEmployeesWithPosition",
        attributeNodes = {
                @NamedAttributeNode("form"),
                @NamedAttributeNode(value = "employees", subgraph = "employees.position")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "employees.position",
                        attributeNodes = @NamedAttributeNode("position")
                )
        }
)
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long registrationNumber;
    private String name;
    private String address;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "form_id")
    private Form form;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Employee> employees = new ArrayList<>();

    public Company() {
    }

    public Company(long registrationNumber, String name, String address, Form form) {
        this.registrationNumber = registrationNumber;
        this.name = name;
        this.address = address;
        this.form = form;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(long registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }
}

