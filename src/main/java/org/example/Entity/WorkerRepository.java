package org.example.Entity;

import java.util.List;

public interface WorkerRepository {
    public boolean saveWorker(Workers worker);
    public Workers getWorker(int id);
    public Workers getWorkerByEmail(String emial);
    public List<Workers> getWorkers();
    public  boolean changeDadataWorker(Workers worker);
    public  boolean delateWorker(int id);


}
