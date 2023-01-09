package org.main.Entity.Temporaty;

public class DailyStatusWork {
    String name;
    String status;
    int count;

    public DailyStatusWork(String name, String status, int count) {
        this.name = name;
        this.status = status;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
