package org.main.Entity.Temporaty;

import org.main.Entity.Tires;

import java.time.LocalDateTime;

public class TireDepartmentTime extends Tires {
    String departmentName;
    LocalDateTime lastDateTime;

    public TireDepartmentTime(String departmentName, LocalDateTime lastDateTime) {
        this.departmentName = departmentName;
        this.lastDateTime = lastDateTime;
    }

    public TireDepartmentTime(int idTire, int height, int width, int diameter, String tag, String speedIndex, String loadIndex, String departmentName, LocalDateTime lastDateTime) {
        super(idTire, height, width, diameter, tag, speedIndex, loadIndex);
        this.departmentName = departmentName;
        this.lastDateTime = lastDateTime;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public LocalDateTime getLastDateTime() {
        return lastDateTime;
    }

    public void setLastDateTime(LocalDateTime lastDateTime) {
        this.lastDateTime = lastDateTime;
    }
}
