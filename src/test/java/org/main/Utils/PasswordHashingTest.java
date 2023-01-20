package org.main.Utils;

import org.junit.Test;
import org.main.Utils.PasswordHashing;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.main.Utils.PasswordHashing.hashPassword;

public class PasswordHashingTest {
    @Test
    public void testDoHashing() {
        String password = "password123";
        String hashed = PasswordHashing.doHashing(password);
        assertNotNull(hashed);
        assertNotEquals(password, hashed);
    }

    @Test
    public void testHashPassword() {
        char[] password = "password".toCharArray();
        byte[] salt = new byte[]{1, 2, 3, 4, 5, 6, 7, 8};
        int iterations = 100;
        int keyLength = 512;

        byte[] hashedPassword = hashPassword(password, salt, iterations, keyLength);

        assertNotNull(hashedPassword);
        assertTrue(hashedPassword.length == 64);

        // Test that the hash is different for different passwords
        char[] wrongPassword = "wrongpassword".toCharArray();
        byte[] wrongHashedPassword = hashPassword(wrongPassword, salt, iterations, keyLength);
        assertFalse(Arrays.equals(hashedPassword, wrongHashedPassword));

        // Test that the hash is different for different salts
        byte[] differentSalt = new byte[]{8, 7, 6, 5, 4, 3, 2, 1};
        byte[] differentHashedPassword = hashPassword(password, differentSalt, iterations, keyLength);
        assertFalse(Arrays.equals(hashedPassword, differentHashedPassword));

    }
}
