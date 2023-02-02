package org.main.Entity;

import org.junit.Test;

import static org.junit.Assert.*;

import org.main.Entity.Departments;
import org.main.Entity.DepartmentsRepositoryImpl;
import org.main.Utils.Temporary;

import java.util.List;


import static org.junit.Assert.assertTrue;

public class DepartmentsRepositoryImplTest {

    @Test
    public void testGetDepartments() {
        DepartmentsRepositoryImpl repository = new DepartmentsRepositoryImpl();
        List<Departments> departments = repository.getDepartments();
        assertNotNull(departments);
        assertTrue(departments.size() > 0);
    }

    @Test
    public void testSaveDepartment() {
        Workers workers = new Workers();
        workers.setFirstName("test");
        workers.setLastName("test");
        workers.setPosition("test");
        Temporary.setWorkers(workers);
        Departments department = new Departments();

        department.setName("Test Department");
        department.setCity("Test City");
        department.setStreet("Test Street");
        department.setFlatNumber("123");
        department.setPostCode("12345");
        department.setHallName("Test Hall");
        department.setPhoneNumber("1234567890");
        DepartmentsRepositoryImpl repository = new DepartmentsRepositoryImpl();
        boolean result = repository.saveDepartment(department);
        assertTrue(result);
        // Check if department was saved to the database
        Departments savedDepartment = repository.getDepartmentByName("Test Department");
        assertNotNull(savedDepartment);
        assertEquals("Test City", savedDepartment.getCity());
        // Clean up
        repository.delateDepartment(savedDepartment.getIdDepartment());
    }
}
