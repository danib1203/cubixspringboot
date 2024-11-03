package hu.cubix.hr.model;

import jakarta.persistence.*;

@Entity
public class Form {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Form() {
    }

    public Form(String formName) {
        this.name = formName;
    }

    public String getName() {
        return name;
    }

    public void setName(String formName) {
        this.name = formName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Form{" +
                "id=" + id +
                ", formName='" + name + '\'' +
                '}';
    }
}
