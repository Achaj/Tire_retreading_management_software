package org.main.Entity;

import org.main.Entity.Temporaty.DaytimeWork;

import java.util.List;

public interface WorkingTimeRepository {

    public WorkingTime getWorkingTimeByID(int id);
    public List<WorkingTime> getListWorkingTime();
    public boolean save(WorkingTime  workingTime);
    public boolean change(WorkingTime workingTime);
    public boolean remove(WorkingTime workingTime);
    public List<DaytimeWork> getListOfEmployeeMinutesWorked(int id);
}