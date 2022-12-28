package org.main.Entity;

import java.util.List;

public interface WorkingTimeRepository {

    public WorkingTime getWorkingTimeByID(int id);
    public List<WorkingTime> getListWorkingTime();
    public boolean save(WorkingTime  workingTime);
    public boolean change(WorkingTime workingTime);
    public boolean delate(WorkingTime workingTime);
}