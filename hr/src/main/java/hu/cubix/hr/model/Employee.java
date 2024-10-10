package hu.cubix.hr.model;

import java.time.LocalDate;

public class Employee {
    private long id;
    private String name;
    private String job;
    private int salary;
    private LocalDate workingSince;

    public Employee(long id, String name, String job, int salary, LocalDate workingSince) {
        this.id = id;
        this.name = name;
        this.job = job;
        this.salary = salary;
        this.workingSince = workingSince;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", job='" + job + '\'' +
                ", salary=" + salary +
                ", workingSince=" + workingSince +
                '}';
    }

    public Employee() {
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

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }


}
