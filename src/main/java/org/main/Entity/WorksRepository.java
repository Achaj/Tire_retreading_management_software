package org.main.Entity;

import org.main.Entity.Temporaty.DailyStatusWork;
import org.main.Entity.Temporaty.WorkNameDate;

import java.util.List;
import java.util.Map;

public interface WorksRepository {
    public Works getWorkByID(int id);
    public Works getLastWorkByTire(int idTire);
    public List<Works> getListWorksByTire(int id);
    public List<Works> getListWorksByWorker(int id);
    public List<Works> getListWorks();
    public Works saveWork(Works works);
    public boolean save(Works  works);
    public boolean change(Works works);
    public boolean remove(int id);
    public Map<String,Integer> countWorkStatus(Departments departments);
    public List<WorkNameDate> countWorkname(Departments departments);
    public List<DailyStatusWork> countDailyWorkStatus(Departments departments);
}
