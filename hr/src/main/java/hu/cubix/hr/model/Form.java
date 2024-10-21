package hu.cubix.hr.model;

import jakarta.persistence.*;

@Entity
public class Form {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String formName;

    public Form() {
    }

    public Form(String formName) {
        this.formName = formName;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
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
                ", formName='" + formName + '\'' +
                '}';
    }
}
