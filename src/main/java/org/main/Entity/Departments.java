package org.main.Entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Departments", schema = "public")
public class Departments implements Serializable {
    @Id
    @Column(name = "id_department")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDepartment;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "city", nullable = false)
    private String city;
    @Column(name = "street", nullable = false)
    private String street;
    @Column(name = "flat_number", nullable = false)
    private String flatNumber;
    @Column(name = "post_code", nullable = false)
    private String postCode;
    @Column(name = "hall_name", nullable = false)
    private String hallName;
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @OneToMany(mappedBy = "departments")
    private List<Workers> workersList = new ArrayList<>();

    @OneToMany(mappedBy = "departments")
    private List<Works> worksList = new ArrayList<>();

    public List<Works> getWorksList() {
        return worksList;
    }


    public Departments() {
    }

    public Departments(int idDepartment, String name, String city, String street, String flatNumber, String postCode, String hallName, String phoneNumber) {
        this.idDepartment = idDepartment;
        this.name = name;
        this.city = city;
        this.street = street;
        this.flatNumber = flatNumber;
        this.postCode = postCode;
        this.hallName = hallName;
        this.phoneNumber = phoneNumber;
    }

    public Departments(int idDepartment, String name, String city, String street, String flatNumber, String postCode, String hallName, String phoneNumber, List<Workers> workersList, List<Works> worksList) {
        this.idDepartment = idDepartment;
        this.name = name;
        this.city = city;
        this.street = street;
        this.flatNumber = flatNumber;
        this.postCode = postCode;
        this.hallName = hallName;
        this.phoneNumber = phoneNumber;
        this.workersList = workersList;
        this.worksList = worksList;
    }

    public int getIdDepartment() {
        return idDepartment;
    }

    public void setIdDepartment(int idDepartment) {
        this.idDepartment = idDepartment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Workers> getWorkersList() {
        return workersList;
    }

    public void setWorkersList(List<Workers> workersList) {
        this.workersList = workersList;
    }

    public void setWorksList(List<Works> worksList) {
        this.worksList = worksList;
    }

    @Override
    public String toString() {
        return name + " " + hallName;
    }


    public String toStringLong() {
        return "Departments{" +
                "idDepartment=" + idDepartment +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", flatNumber='" + flatNumber + '\'' +
                ", postCode='" + postCode + '\'' +
                ", hallName='" + hallName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", workersList=" + workersList +
                ", worksList=" + worksList +
                '}';
    }
}
