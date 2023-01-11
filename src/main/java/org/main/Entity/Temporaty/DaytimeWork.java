package org.main.Entity.Temporaty;

import java.sql.Date;

public class DaytimeWork {

    Date date;
    int minutes;

    public DaytimeWork(Date date, int minutes) {
        this.date = date;
        this.minutes = minutes;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
}
