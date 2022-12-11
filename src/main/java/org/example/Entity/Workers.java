package org.example.Entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

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
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "tag", nullable = false)
    private String tag;
    @Column(name = "employment_date", nullable = false)
    private Date enploymentDate;
    @Column(name = "stacking_date", nullable =false)
    private Date stackingDate;

    public Workers() {
    }

    public Workers(int idWorker, String firstName, String lastName, String email, String password, String tag, Date enploymentDate, Date stackingDate) {
        this.idWorker = idWorker;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.tag = tag;
        this.enploymentDate = enploymentDate;
        this.stackingDate = stackingDate;
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

    public Date getEnploymentDate() {
        return enploymentDate;
    }

    public Date getStackingDate() {
        return stackingDate;
    }

    public void setEnploymentDate(Date enploymentDate) {
        this.enploymentDate = enploymentDate;
    }

    public void setStackingDate(Date stackingDate) {
        this.stackingDate = stackingDate;
    }
}

