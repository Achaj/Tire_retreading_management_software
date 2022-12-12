package org.example.Entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Tires", schema = "public")
public class Tires implements Serializable {
    @Id
    @Column(name = "id_tire")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTire;
    @Column(name = "height", nullable = false)
    private int height;
    @Column(name = "width", nullable = false)
    private int width;
    @Column(name = "diameter", nullable = false)
    private int diameter;
    @Column(name = "tag", nullable = false)
    private String tag;
    @Column(name = "status", nullable = false)
    private String status;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "tires")
    private List<Works> worksList = new ArrayList<>();



    public Tires() {
    }

    public Tires(int idTire, int height, int width, int diameter, String tag, String status, List<Works> worksList) {
        this.idTire = idTire;
        this.height = height;
        this.width = width;
        this.diameter = diameter;
        this.tag = tag;
        this.status = status;
        this.worksList = worksList;
    }

    public int getIdTire() {
        return idTire;
    }

    public void setIdTire(int idTire) {
        this.idTire = idTire;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getDiameter() {
        return diameter;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Works> getWorksList() {
        return worksList;
    }

    public void setWorksList(List<Works> worksList) {
        this.worksList = worksList;
    }
}
