package hu.cubix.hr.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
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

}

