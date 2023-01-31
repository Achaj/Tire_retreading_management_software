package org.main.Entity;

import org.junit.Test;

import static org.junit.Assert.*;

public class TiresTest {
    @Test
    public void testTire() {
        Tires tires = new Tires(1, 205, 55, 16, "TireTag123", "H", "95");
        assertEquals(1, tires.getIdTire());
        assertEquals(205, tires.getHeight());
        assertEquals(55, tires.getWidth());
        assertEquals(16, tires.getDiameter());
        assertEquals("TireTag123", tires.getTag());
        assertEquals("H", tires.getSpeedIndex());
        assertEquals("95", tires.getLoadIndex());
    }
}