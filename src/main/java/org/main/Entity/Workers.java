package org.main.Entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Workers", schema = "public")
public class Workers implements Serializable {
    @Id
    @Column(name = "id_worker")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idWorker;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "email",unique=true, nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "tag",unique=true, nullable = false)
    private String tag;
    @Column(name = "position", nullable = false)
    private String position;
    @Column(name = "employment_date", nullable = false)
    private Date enploymentDate;
    @Column(name = "stacking_date", nullable =true)
    private Date stackingDate;

    @ManyToOne
    @JoinColumn(name = "id_department", nullable = false)
    private Departments departments;

    @OneToMany(mappedBy = "workers")
    private List<Works> worksList = new ArrayList<>();

    @OneToMany(mappedBy = "workers", cascade = CascadeType.ALL)
    private List<WorkingTime> workingTimes = new ArrayList<>();


    public Workers() {
    }

    public Workers(int idWorker, String firstName, String lastName, String email, String password, String tag, String position, Date enploymentDate, Date stackingDate) {
        this.idWorker = idWorker;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.tag = tag;
        this.position = position;
        this.enploymentDate = enploymentDate;
        this.stackingDate = stackingDate;
    }

    public void setWorksList(List<Works> worksList) {
        this.worksList = worksList;
    }

    public List<WorkingTime> getWorkingTimes() {
        return workingTimes;
    }

    public void setWorkingTimes(List<WorkingTime> workingTimes) {
        this.workingTimes = workingTimes;
    }

    public int getIdWorker() {
        return idWorker;
    }

    public void setIdWorker(int idWorker) {
        this.idWorker = idWorker;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getEnploymentDate() {
        return enploymentDate;
    }

    public void setEnploymentDate(Date enploymentDate) {
        this.enploymentDate = enploymentDate;
    }

    public Date getStackingDate() {
        return stackingDate;
    }

    public void setStackingDate(Date stackingDate) {
        this.stackingDate = stackingDate;
    }

    public Departments getDepartments() {
        return departments;
    }

    public void setDepartments(Departments departments) {
        this.departments = departments;
    }

    public List<Works> getWorksList() {
        return worksList;
    }

    @Override
    public String toString() {
        return
                firstName + "-" +lastName + "-" + position ;
    }
}

