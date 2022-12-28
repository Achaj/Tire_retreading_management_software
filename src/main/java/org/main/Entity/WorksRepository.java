package org.main.Entity;

import java.util.List;

public interface WorksRepository {
    public Works getWorkByID(int id);
    public Works getLastWorkByTire(int idTire);
    public List<Works> getListWorksByTire(int id);
    public List<Works> getListWorks();
    public boolean save(Works  works);
    public boolean change(Works works);
    public boolean delate(int id);
}
