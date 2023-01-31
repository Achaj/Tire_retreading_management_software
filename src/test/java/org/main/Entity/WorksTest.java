package org.main.Entity;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class WorksTest {
    @Test
    public void testGetIdWork() {
        Works works = new Works();
        works.setIdWork(1);
        assertEquals(1, works.getIdWork());
    }

    @Test
    public void testGetName() {
        Works works = new Works();
        works.setName("Test Work");
        assertEquals("Test Work", works.getName());
    }

    @Test
    public void testGetDateStart() {
        Works works = new Works();
        LocalDateTime date = LocalDateTime.now();
        works.setDateStart(date);
        assertEquals(date, works.getDateStart());
    }

    @Test
    public void testGetDateStop() {
        Works works = new Works();
        LocalDateTime date = LocalDateTime.now();
        works.setDateStop(date);
        assertEquals(date, works.getDateStop());
    }

    @Test
    public void testGetStatus() {
        Works works = new Works();
        works.setStatus("Test Status");
        assertEquals("Test Status", works.getStatus());
    }

    @Test
    public void testGetWorkers() {
        Workers workers = new Workers();
        workers.setFirstName("Test First Name");
        workers.setLastName("Test Last Name");

        Works works = new Works();
        works.setWorkers(workers);
        assertEquals(workers, works.getWorkers());
    }

    @Test
    public void testGetTires() {
        Tires tires = new Tires();
        tires.setTag("11111111");

        Works works = new Works();
        works.setTires(tires);
        assertEquals(tires.getTag(), works.getTires().getTag());
    }

    @Test
    public void testGetDepartments() {
        Departments departments = new Departments();
        departments.setName("Test Department Name");

        Works works = new Works();
        works.setDepartments(departments);
        assertEquals(departments, works.getDepartments());
    }


}