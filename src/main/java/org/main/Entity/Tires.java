package org.main.Entity;

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
    @Column(name = "tag", nullable = false, unique = true)
    private String tag;
    @Column(name = "speed_index", nullable = false)
    private String speedIndex;
    @Column(name = "load_index", nullable = false)
    private String loadIndex;


    @OneToMany( mappedBy = "tires")
    private List<Works> worksList = new ArrayList<>();



    public Tires() {
    }

    public Tires(int idTire, int height, int width, int diameter, String tag,  String speedIndex, String loadIndex) {
        this.idTire = idTire;
        this.height = height;
        this.width = width;
        this.diameter = diameter;
        this.tag = tag;
        this.speedIndex = speedIndex;
        this.loadIndex = loadIndex;
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



    public String getSpeedIndex() {
        return speedIndex;
    }

    public void setSpeedIndex(String speedIndex) {
        this.speedIndex = speedIndex;
    }

    public String getLoadIndex() {
        return loadIndex;
    }

    public void setLoadIndex(String loadIndex) {
        this.loadIndex = loadIndex;
    }

    public List<Works> getWorksList() {
        return worksList;
    }

    public void setWorksList(List<Works> worksList) {
        this.worksList = worksList;
    }

    @Override
    public String toString() {
        return
                "idTire=" + idTire +
                ", height=" + height +
                ", width=" + width +
                ", diameter=" + diameter +
                ", tag='" + tag +
                ", speedIndex=" + speedIndex  +
                ", loadIndex=" + loadIndex ;
    }
}
