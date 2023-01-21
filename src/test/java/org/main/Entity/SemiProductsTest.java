package org.main.Entity;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class SemiProductsTest {
    @Test
    public void testGetIdSemiProduct() {
        SemiProducts semiProduct = new SemiProducts(1, "Semi Product 1", "Category 1", "Tag1", 10, new ArrayList<>());
        assertEquals(1, semiProduct.getIdSemiProduct());
    }

    @Test
    public void testSetIdSemiProduct() {
        SemiProducts semiProduct = new SemiProducts();
        semiProduct.setIdSemiProduct(1);
        assertEquals(1, semiProduct.getIdSemiProduct());
    }

    @Test
    public void testGetName() {
        SemiProducts semiProduct = new SemiProducts(1, "Semi Product 1", "Category 1", "Tag1", 10, new ArrayList<>());
        assertEquals("Semi Product 1", semiProduct.getName());
    }

    @Test
    public void testSetName() {
        SemiProducts semiProduct = new SemiProducts();
        semiProduct.setName("Semi Product 1");
        assertEquals("Semi Product 1", semiProduct.getName());
    }

    @Test
    public void testGetCategory() {
        SemiProducts semiProduct = new SemiProducts(1, "Semi Product 1", "Category 1", "Tag1", 10, new ArrayList<>());
        assertEquals("Category 1", semiProduct.getCategory());
    }

    @Test
    public void testSetCategory() {
        SemiProducts semiProduct = new SemiProducts();
        semiProduct.setCategory("Category 1");
        assertEquals("Category 1", semiProduct.getCategory());
    }

    @Test
    public void testGetTag() {
        SemiProducts semiProduct = new SemiProducts(1, "Semi Product 1", "Category 1", "Tag1", 10, new ArrayList<>());
        assertEquals("Tag1", semiProduct.getTag());
    }

    @Test
    public void testSetTag() {
        SemiProducts semiProduct = new SemiProducts();
        semiProduct.setTag("Tag1");
        assertEquals("Tag1", semiProduct.getTag());
    }

    @Test
    public void testGetAmount() {
        SemiProducts semiProduct = new SemiProducts(1, "Semi Product 1", "Category 1", "Tag1", 10, new ArrayList<>());
        assertEquals(10, semiProduct.getAmount());
    }

    @Test
    public void testSetAmount() {
        SemiProducts semiProduct = new SemiProducts();
        semiProduct.setAmount(10);
        assertEquals(10, semiProduct.getAmount());
    }

}