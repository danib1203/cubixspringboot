package hu.cubix.hr.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
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

    @Override
    public String toString() {
        return "Form{" +
                "id=" + id +
                ", formName='" + name + '\'' +
                '}';
    }
}
