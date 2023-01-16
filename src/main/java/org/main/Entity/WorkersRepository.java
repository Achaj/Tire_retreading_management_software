package org.main.Entity;

import org.jetbrains.annotations.NotNull;
import org.main.Entity.Temporaty.EmployeesOverworkedTime;

import java.sql.Date;
import java.util.List;

public interface WorkersRepository {
    public boolean saveWorker(Workers worker);
    public Workers getWorker(int id);
    public Workers getWorkerByEmail(String emial);
    public Workers getWorkerByTag(String tag);
    public List<Workers> getWorkers();
    public List<Workers> getWorkersByPosition(String position);
    public List<Workers> getWorkersByDepartment(int id);
    public  boolean changeDadataWorker(Workers worker);
    public  boolean delateWorker(int id);
    public List<EmployeesOverworkedTime> getEmployeesOverworkedTimeList(Workers workers, Date date);
    public List<EmployeesOverworkedTime> getEmployeesOverworkedTimeByDepatmentList(Departments departments, Date date);
    public List<EmployeesOverworkedTime> getEmployeesOverworkedTimeByDaysList(@NotNull Workers workers,@NotNull Date date);


}
