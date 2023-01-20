package org.main.Entity;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.junit.Before;
import org.junit.Test;
import org.main.Entity.ConnectionToDB;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class ConnectionToDBTest {
    @Mock
    private EntityManagerFactory mockEntityManagerFactory;
    @Mock
    private EntityManager mockEntityManager;
    @Mock
    private EntityTransaction mockEntityTransaction;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetEntityManagerFactory_returnsValidObject() {
        ConnectionToDB.entityManagerFactory = mockEntityManagerFactory;
        EntityManagerFactory result = ConnectionToDB.getEntityManagerFactory();
        assertNotNull(result);
    }

    @Test
    public void testGetEntityManagerFactory_initializesEntityManagerAndTransaction() {
        ConnectionToDB.entityManagerFactory = mockEntityManagerFactory;
        ConnectionToDB.getEntityManagerFactory();
        assertNotNull(ConnectionToDB.entityManager);
        assertNotNull(ConnectionToDB.entityTransaction);
    }


}
