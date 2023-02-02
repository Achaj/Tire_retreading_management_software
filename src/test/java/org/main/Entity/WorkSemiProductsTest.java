package org.main.Entity;

import org.junit.Test;

import static org.junit.Assert.*;

public class WorkSemiProductsTest {
    @Test
    public void testGettersAndSetters() {
        Works works = new Works();
        SemiProducts semiProducts = new SemiProducts();

        WorkSemiProducts workSemiProducts = new WorkSemiProducts();
        workSemiProducts.setIdWorkSemiProduct(1);
        workSemiProducts.setAmount(10);
        workSemiProducts.setWorks(works);
        workSemiProducts.setSemiProducts(semiProducts);

        assertEquals(1, workSemiProducts.getIdWorkSemiProduct());
        assertEquals(10, workSemiProducts.getAmount());
        assertEquals(works, workSemiProducts.getWorks());
        assertEquals(semiProducts, workSemiProducts.getSemiProducts());
    }
}