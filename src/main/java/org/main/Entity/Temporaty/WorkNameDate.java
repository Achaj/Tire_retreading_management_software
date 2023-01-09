package org.main.Entity.Temporaty;

import java.sql.Date;

public class WorkNameDate {
    Date date;
    String name;
    int count;

    public WorkNameDate(Date date, String name, int count) {
        this.date = date;
        this.name = name;
        this.count = count;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
