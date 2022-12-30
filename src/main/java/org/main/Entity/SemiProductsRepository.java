package org.main.Entity;

import java.util.List;

public interface SemiProductsRepository {
    public SemiProducts getSemiProductsById(int id);
    public SemiProducts getSemiProductsTag(String tag);
    public List<SemiProducts> getSemiProducts();
    public boolean saveSemiProduct(SemiProducts semiProducts);
    public  boolean changeSemiProduct(SemiProducts semiProducts);
    public  boolean delateSemiProduct(int id);
}
