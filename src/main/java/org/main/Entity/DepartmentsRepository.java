package org.main.Entity;

import java.util.List;

public interface DepartmentsRepository {
    public List<Departments> getDepartments();

    public boolean saveDepartment(Departments departments);
    public Departments getDepartment(int id);
    public Departments getDepartmentByName(String name);
    public List<Departments> getDepartmentByCity(String city);
    public  boolean changeDepartment(Departments departments);
    public  boolean delateDepartment(int id);
}
