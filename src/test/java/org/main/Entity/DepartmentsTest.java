package org.main.Entity;

import org.junit.Test;

import static org.junit.Assert.*;

public class DepartmentsTest {
    @Test
    public void testDepartmentProperties() {
        Departments department = new Departments();
        department.setName("Marketing");
        department.setCity("New York");
        department.setStreet("5th Avenue");
        department.setFlatNumber("10");
        department.setPostCode("10001");
        department.setHallName("Empire State Building");
        department.setPhoneNumber("555-555-5555");

        assertEquals("Marketing", department.getName());
        assertEquals("New York", department.getCity());
        assertEquals("5th Avenue", department.getStreet());
        assertEquals("10", department.getFlatNumber());
        assertEquals("10001", department.getPostCode());
        assertEquals("Empire State Building", department.getHallName());
        assertEquals("555-555-5555", department.getPhoneNumber());
    }
}