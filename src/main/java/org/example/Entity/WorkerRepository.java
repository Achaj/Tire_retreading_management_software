package org.example.Entity;

public interface WorkerRepository {
    public boolean saveWorker(Workers worker);
    public Workers getWorker(int id);
    public Workers getWorkerByEmail(String emial);
    public  boolean changeDadataWorker(Workers worker);
    public  boolean delateWorker(int id);


}
