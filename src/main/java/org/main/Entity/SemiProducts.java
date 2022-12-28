package org.main.Entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Semi_Products", schema = "public")
public class SemiProducts implements Serializable {
    @Id
    @Column(name = "id_semi_product")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idSemiProduct;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "category", nullable = false)
    private String category;
    @Column(name = "tag", nullable = false ,unique = true)
    private String tag;
    @Column(name = "amount", nullable = false)
    private int amount;

    @OneToMany(mappedBy = "semiProducts")
    private List<WorkSemiProducts> workSemiProducts = new ArrayList<>();

    public SemiProducts() {
    }

    public SemiProducts(int idSemiProduct, String name, String category, String tag, int amount, List<WorkSemiProducts> workSemiProducts) {
        this.idSemiProduct = idSemiProduct;
        this.name = name;
        this.category = category;
        this.tag = tag;
        this.amount = amount;
        this.workSemiProducts = workSemiProducts;
    }

    public int getIdSemiProduct() {
        return idSemiProduct;
    }

    public void setIdSemiProduct(int idSemiProduct) {
        this.idSemiProduct = idSemiProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public List<WorkSemiProducts> getWorkSemiProducts() {
        return workSemiProducts;
    }

    public void setWorkSemiProducts(List<WorkSemiProducts> workSemiProducts) {
        this.workSemiProducts = workSemiProducts;
    }
}