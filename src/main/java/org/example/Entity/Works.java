package org.example.Entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Works", schema = "public")
public class Works implements Serializable {
    @Id
    @Column(name = "id_work")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idWork;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "date", nullable = false)
    private Date date;
    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "id_worker", nullable = false)
    private Workers workers;

    @ManyToOne
    @JoinColumn(name = "id_tire", nullable = false)
    private Tires tires;

    @ManyToOne
    @JoinColumn(name = "id_department", nullable = false)
    private Departments departments;

    @OneToMany(mappedBy = "works")
    private List<WorkSemiProducts> workSemiProducts = new ArrayList<>();

    public List<WorkSemiProducts> getWorkSemiProducts() {
        return workSemiProducts;
    }

    public Works() {
    }

    public Works(int idWork, String name, Date date, String status, Workers workers, Tires tires, Departments departments, List<WorkSemiProducts> workSemiProducts) {
        this.idWork = idWork;
        this.name = name;
        this.date = date;
        this.status = status;
        this.workers = workers;
        this.tires = tires;
        this.departments = departments;
        this.workSemiProducts = workSemiProducts;
    }

    public int getIdWork() {
        return idWork;
    }

    public void setIdWork(int idWork) {
        this.idWork = idWork;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Workers getWorkers() {
        return workers;
    }

    public void setWorkers(Workers workers) {
        this.workers = workers;
    }

    public Tires getTires() {
        return tires;
    }

    public void setTires(Tires tires) {
        this.tires = tires;
    }

    public Departments getDepartments() {
        return departments;
    }

    public void setDepartments(Departments departments) {
        this.departments = departments;
    }

    public void setWorkSemiProducts(List<WorkSemiProducts> workSemiProducts) {
        this.workSemiProducts = workSemiProducts;
    }
}
