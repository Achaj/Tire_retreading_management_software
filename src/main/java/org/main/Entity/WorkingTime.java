package org.main.Entity;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "Working_Time", schema = "public")
public class WorkingTime {
    @Id
    @Column(name = "id_working_time")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idWorkingTime;
    @Column(name = "date_login", nullable = false)
    private LocalDateTime dateLogin;
    @Column(name = "date_logout", nullable = true)
    private LocalDateTime dateLogOut;

    @ManyToOne
    @JoinColumn(name = "id_worker", nullable = false)
    private Workers workers;

    public int getIdWorkingTime() {
        return idWorkingTime;
    }

    public void setIdWorkingTime(int idWorkingTime) {
        this.idWorkingTime = idWorkingTime;
    }

    public LocalDateTime getDateLogin() {
        return dateLogin;
    }

    public void setDateLogin(LocalDateTime dateLogin) {
        this.dateLogin = dateLogin;
    }

    public LocalDateTime getDateLogOut() {
        return dateLogOut;
    }

    public void setDateLogOut(LocalDateTime dateLogOut) {
        this.dateLogOut = dateLogOut;
    }

    public Workers getWorkers() {
        return workers;
    }

    public void setWorkers(Workers workers) {
        this.workers = workers;
    }

    @Override
    public String toString() {
        return "WorkingTime{" +
                "idWorkingTime=" + idWorkingTime +
                ", dateLogin=" + dateLogin +
                ", dateLogOut=" + dateLogOut +
                ", workers=" + workers +
                '}';
    }
}
