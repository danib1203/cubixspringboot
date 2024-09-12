package hu.cubix.hr.model;

import java.time.LocalDateTime;

public class Employee {
    private long id;
    private String job;
    private int salary;
    private LocalDateTime workingSince;

    public Employee(long id, String job, int salary, LocalDateTime workingSince) {
        this.id = id;
        this.job = job;
        this.salary = salary;
        this.workingSince = workingSince;
    }

    public LocalDateTime getWorkingSince() {
        return workingSince;
    }

    public void setWorkingSince(LocalDateTime workingSince) {
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
