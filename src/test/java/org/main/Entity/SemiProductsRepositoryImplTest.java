package org.main.Entity;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class SemiProductsRepositoryImplTest {
    private SemiProductsRepositoryImpl repository = new SemiProductsRepositoryImpl();

    @Test
    public void testSaveSemiProduct() {
        // Create a new SemiProducts object
        SemiProducts semiProduct = new SemiProducts(0, "Test Product", "Test Category", "test-tag", 10);

        // Call the saveSemiProduct method
        boolean result = repository.saveSemiProduct(semiProduct);

        // Assert that the method returned true, indicating that the save was successful
        Assert.assertTrue(result);

        // Get the semiProduct from the database using the id
        SemiProducts savedSemiProduct = repository.getSemiProductsById(semiProduct.getIdSemiProduct());

        // Assert that the semiProduct returned from the database is the same as the one that was saved
        Assert.assertEquals(semiProduct.getTag(), savedSemiProduct.getTag());
        // Clean up
        repository.delateSemiProduct(semiProduct.getIdSemiProduct());
    }
}