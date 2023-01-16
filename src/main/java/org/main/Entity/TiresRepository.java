package org.main.Entity;

import org.main.Entity.Temporaty.TireDepartmentTime;

import java.util.List;

public interface TiresRepository {
    public Tires getTireById(int id);

    public Tires getTireByTag(String tag);

    public List<Tires> getTires();

    public boolean saveTire(Tires tire);

    public Tires save(Tires tire);

    public boolean changeTire(Tires tire);

    public boolean delateTire(int id);

    public List<TireDepartmentTime> getTiresStockLevel(Departments departments);
}
