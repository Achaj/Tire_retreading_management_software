package org.main.Entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;
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
    @Column(name = "date_start", nullable = true)
    private LocalDateTime dateStart;
    @Column(name = "date_stop", nullable = true)
    private LocalDateTime dateStop;
    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_worker")
    private Workers workers;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_tire", nullable = false)
    private Tires tires;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_department", nullable = false)
    private Departments departments;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "works", cascade = CascadeType.ALL)
    private List<WorkSemiProducts> workSemiProducts = new ArrayList<>();

    public void setWorkSemiProducts(List<WorkSemiProducts> workSemiProducts) {
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

    public LocalDateTime getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDateTime dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDateTime getDateStop() {
        return dateStop;
    }

    public void setDateStop(LocalDateTime dateStop) {
        this.dateStop = dateStop;
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

    public List<WorkSemiProducts> getWorkSemiProducts() {
        return workSemiProducts;
    }

    public Works() {
    }

    @Override
    public String toString() {
        return
                "nazwa ='" + name +
                        ", dateStop=" + dateStop +
                        ", status='" + status + '\'' +
                        ", dział=" + departments.getName() +
                        '}';
    }

    public String toStringLong() {
        return "Works{" +
                "idWork=" + idWork +
                ", name='" + name + '\'' +
                ", dateStart=" + dateStart +
                ", dateStop=" + dateStop +
                ", status='" + status + '\'' +
                ", workers=" + workers +
                ", tires=" + tires +
                ", departments=" + departments +
                ", workSemiProducts=" + workSemiProducts +
                '}';
    }

}
