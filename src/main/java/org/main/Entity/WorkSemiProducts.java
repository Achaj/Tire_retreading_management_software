package org.main.Entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Work_Semi_Products", schema = "public")
public class WorkSemiProducts implements Serializable {
    @Id
    @Column(name = "id_work_semi_product")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idWorkSemiProduct;
    @Column(name = "amount", nullable = false)
    private int amount;

    @ManyToOne
    @JoinColumn(name = "id_work", nullable = false)
    private Works works;

    @ManyToOne
    @JoinColumn(name = "id_semi_product", nullable = false)
    private SemiProducts semiProducts;

    public WorkSemiProducts() {
    }

    public WorkSemiProducts(int idWorkSemiProduct, int amount, Works works, SemiProducts semiProducts) {
        this.idWorkSemiProduct = idWorkSemiProduct;
        this.amount = amount;
        this.works = works;
        this.semiProducts = semiProducts;
    }

    public int getIdWorkSemiProduct() {
        return idWorkSemiProduct;
    }

    public void setIdWorkSemiProduct(int idWorkSemiProduct) {
        this.idWorkSemiProduct = idWorkSemiProduct;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Works getWorks() {
        return works;
    }

    public void setWorks(Works works) {
        this.works = works;
    }

    public SemiProducts getSemiProducts() {
        return semiProducts;
    }

    public void setSemiProducts(SemiProducts semiProducts) {
        this.semiProducts = semiProducts;
    }
}
