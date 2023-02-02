package org.main.Entity;

import org.junit.Test;
import org.main.Utils.Temporary;

import java.util.List;

import static org.junit.Assert.*;

public class TiresRepositoryImplTest {
    @Test
    public void testGetTireById() {
        Workers workers = new Workers();
        workers.setFirstName("test");
        workers.setLastName("test");
        workers.setPosition("test");
        Temporary.setWorkers(workers);
        TiresRepository tiresRepository = new TiresRepositoryImpl();
        Tires tire = new Tires(0, 225, 45, 17, "tag1", "H", "91");
        tiresRepository.saveTire(tire);

        Tires retrievedTire = tiresRepository.getTireById(tire.getIdTire());
        assertNotNull(retrievedTire);
        assertEquals(tire.getIdTire(), retrievedTire.getIdTire());
        assertEquals(225, retrievedTire.getHeight());
        assertEquals(45, retrievedTire.getWidth());
        assertEquals(17, retrievedTire.getDiameter());
        assertEquals("tag1", retrievedTire.getTag());
        assertEquals("H", retrievedTire.getSpeedIndex());
        assertEquals("91", retrievedTire.getLoadIndex());
        //clean
        assertTrue(tiresRepository.delateTire(retrievedTire.getIdTire()));
    }

    @Test
    public void testGetTireByTag() {
        Workers workers = new Workers();
        workers.setFirstName("test");
        workers.setLastName("test");
        workers.setPosition("test");
        Temporary.setWorkers(workers);
        TiresRepository tiresRepository = new TiresRepositoryImpl();
        Tires tire = new Tires(0, 225, 45, 17, "tag1", "H", "91");

        tiresRepository.saveTire(tire);

        Tires retrievedTire = tiresRepository.getTireByTag("tag1");
        assertNotNull(retrievedTire);
        assertEquals(tire.getIdTire(), retrievedTire.getIdTire());
        assertEquals(225, retrievedTire.getHeight());
        assertEquals(45, retrievedTire.getWidth());
        assertEquals(17, retrievedTire.getDiameter());
        assertEquals("tag1", retrievedTire.getTag());
        assertEquals("H", retrievedTire.getSpeedIndex());
        assertEquals("91", retrievedTire.getLoadIndex());
        //clean
        assertTrue(tiresRepository.delateTire(retrievedTire.getIdTire()));
    }

}
