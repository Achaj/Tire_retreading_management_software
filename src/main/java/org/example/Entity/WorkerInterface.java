package org.example.Entity;

public interface WorkerInterface {
    public boolean saveWorker(Workers worker);
    public Workers getWorker(int id);
    public  boolean changeDadataWorker(Workers worker);
    public  boolean delateWorker(int id);


}
