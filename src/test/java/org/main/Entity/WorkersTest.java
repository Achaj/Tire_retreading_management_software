package org.main.Entity;

import org.junit.Test;

import java.sql.Date;

import static org.junit.Assert.*;

public class WorkersTest {
    @Test
    public void testSetAndGetIdWorker() {
        Workers workers = new Workers();
        workers.setIdWorker(1);
        assertEquals(1, workers.getIdWorker());
    }

    @Test
    public void testSetAndGetFirstName() {
        Workers workers = new Workers();
        workers.setFirstName("John");
        assertEquals("John", workers.getFirstName());
    }

    @Test
    public void testSetAndGetLastName() {
        Workers workers = new Workers();
        workers.setLastName("Doe");
        assertEquals("Doe", workers.getLastName());
    }

    @Test
    public void testSetAndGetEmail() {
        Workers workers = new Workers();
        workers.setEmail("johndoe@example.com");
        assertEquals("johndoe@example.com", workers.getEmail());
    }

    @Test
    public void testSetAndGetPassword() {
        Workers workers = new Workers();
        workers.setPassword("password123");
        assertEquals("password123", workers.getPassword());
    }

    @Test
    public void testSetAndGetTag() {
        Workers workers = new Workers();
        workers.setTag("johndoe123");
        assertEquals("johndoe123", workers.getTag());
    }

    @Test
    public void testSetAndGetPosition() {
        Workers workers = new Workers();
        workers.setPosition("Manager");
        assertEquals("Manager", workers.getPosition());
    }

    @Test
    public void testSetAndGetEnploymentDate() {
        Workers workers = new Workers();
        Date enploymentDate = new Date(2020, 10, 10);
        workers.setEnploymentDate(enploymentDate);
        assertEquals(enploymentDate, workers.getEnploymentDate());
    }

    @Test
    public void testSetAndGetStackingDate() {
        Workers workers = new Workers();
        Date stackingDate = new Date(2022, 10, 10);
        workers.setStackingDate(stackingDate);
        assertEquals(stackingDate, workers.getStackingDate());
    }
}