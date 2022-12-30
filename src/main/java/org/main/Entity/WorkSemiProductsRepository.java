package org.main.Entity;

import java.util.List;

public interface WorkSemiProductsRepository {
    public boolean save(WorkSemiProducts workSemiProducts);
    public boolean change(WorkSemiProducts workSemiProducts);
    public boolean remove(int id);
    public WorkSemiProducts getWorkSemiProductsByID(int id);
    public List<WorkSemiProducts> getSemiProductsList();
}
