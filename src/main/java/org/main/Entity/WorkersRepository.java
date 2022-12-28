package org.main.Entity;

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


}
