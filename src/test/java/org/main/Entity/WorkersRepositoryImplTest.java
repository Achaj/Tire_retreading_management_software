package org.main.Entity;

import org.junit.Before;
import org.junit.Test;

import java.sql.Date;

import static org.junit.Assert.*;

public class WorkersRepositoryImplTest {
    private WorkersRepositoryImpl workersDAO;
    private DepartmentsRepositoryImpl departmentDAO;
    private Workers worker;
    private Departments departments;
    private final String EMAIL = "johndoe@example.com";
    private final String TAG = "johndoe123";

    @Before
    public void setUp() {
        departmentDAO = new DepartmentsRepositoryImpl();
        workersDAO = new WorkersRepositoryImpl();
        worker = new Workers(0, "John", "Doe", EMAIL, "password123", TAG, "Manager", new Date(2020, 10, 10), null);
        departments = new Departments(0, "TEST", "TEST", "TEST", "1", "11-111", "1t", "111-111-111");

    }

    @Test
    public void testSaveWorker() {

        assertTrue(departmentDAO.saveDepartment(departments));
        // Check if department was saved to the database
        Departments savedDepartment = departmentDAO.getDepartmentByName("TEST");
        worker.setDepartments(departments);
        assertTrue(workersDAO.saveWorker(worker));
        // Clean up
        assertTrue(workersDAO.delateWorker(worker.getIdWorker()));
        assertTrue(departmentDAO.delateDepartment(savedDepartment.getIdDepartment()));

    }

    @Test
    public void testGetWorker() {
        assertTrue(departmentDAO.saveDepartment(departments));
        // Check if department was saved to the database
        Departments savedDepartment = departmentDAO.getDepartmentByName("TEST");
        worker.setDepartments(departments);
        workersDAO.saveWorker(worker);
        assertEquals(worker, workersDAO.getWorker(worker.getIdWorker()));
        assertTrue(workersDAO.delateWorker(worker.getIdWorker()));
        assertTrue(departmentDAO.delateDepartment(savedDepartment.getIdDepartment()));
    }

    @Test
    public void testGetWorkerByEmail() {
        assertTrue(departmentDAO.saveDepartment(departments));
        // Check if department was saved to the database
        Departments savedDepartment = departmentDAO.getDepartmentByName("TEST");
        worker.setDepartments(departments);
        workersDAO.saveWorker(worker);
        assertEquals(worker, workersDAO.getWorkerByEmail(EMAIL));
        assertTrue(workersDAO.delateWorker(worker.getIdWorker()));
        assertTrue(departmentDAO.delateDepartment(savedDepartment.getIdDepartment()));
    }

    @Test
    public void testGetWorkerByTag() {
        assertTrue(departmentDAO.saveDepartment(departments));
        // Check if department was saved to the database
        Departments savedDepartment = departmentDAO.getDepartmentByName("TEST");
        worker.setDepartments(departments);
        workersDAO.saveWorker(worker);
        assertEquals(worker, workersDAO.getWorkerByTag(TAG));
        assertTrue(workersDAO.delateWorker(worker.getIdWorker()));
        assertTrue(departmentDAO.delateDepartment(savedDepartment.getIdDepartment()));
    }

    @Test
    public void testChangeDadataWorker() {
        assertTrue(departmentDAO.saveDepartment(departments));
        // Check if department was saved to the database
        Departments savedDepartment = departmentDAO.getDepartmentByName("TEST");
        worker.setDepartments(departments);
        workersDAO.saveWorker(worker);
        worker.setFirstName("Jane");
        assertTrue(workersDAO.changeDadataWorker(worker));
        assertEquals("Jane", workersDAO.getWorker(worker.getIdWorker()).getFirstName());
        assertTrue(workersDAO.delateWorker(worker.getIdWorker()));
        assertTrue(departmentDAO.delateDepartment(savedDepartment.getIdDepartment()));
    }
}