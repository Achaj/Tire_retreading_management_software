package org.main.Utils;

import org.junit.Assert;
import org.junit.Test;
import org.main.Utils.ValidadiotData;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidityDataTest {
    @Test
    public void testValidatePassword() {
        String validPassword = "Test@123";
        String invalidPassword = "test123";

        assertTrue(ValidadiotData.validatePassword(validPassword));
        assertFalse(ValidadiotData.validatePassword(invalidPassword));
    }

    @Test
    public void testValidateEmail() {
        String validEmail = "test@example.com";
        String invalidEmail = "testexample.com";

        assertTrue(ValidadiotData.validdateEmail(validEmail));
        assertFalse(ValidadiotData.validdateEmail(invalidEmail));
    }

    @Test
    public void validateDate_validDate_returnsTrue() {

        assertTrue(ValidadiotData.validateDate("01.01.2022"));
        assertTrue(ValidadiotData.validateDate("31.12.2022"));
        assertFalse(ValidadiotData.validateDate("1.1.22"));
    }

    @Test
    public void validateDate_invalidDate_returnsFalse() {

        assertFalse(ValidadiotData.validateDate("32.12.2022"));
        assertFalse(ValidadiotData.validateDate("31.13.2022"));
        assertFalse(ValidadiotData.validateDate("01-01-2022"));
    }

    @Test
    public void testValidateName() {

        assertTrue(ValidadiotData.valideteName("JohnSmith"));
        assertTrue(ValidadiotData.valideteName("Mary"));
        assertFalse(ValidadiotData.valideteName("John_Doe"));
        assertFalse(ValidadiotData.valideteName("John Doe123"));
    }

    @Test
    public void testValidateZipCode() {

        assertTrue(ValidadiotData.validateZipCode("00-000"));
        assertTrue(ValidadiotData.validateZipCode("99-999"));
        assertFalse(ValidadiotData.validateZipCode("0000"));
        assertFalse(ValidadiotData.validateZipCode("99999"));
        assertFalse(ValidadiotData.validateZipCode("99-99"));
    }

    @Test
    public void testValidateCity() {

        assertTrue(ValidadiotData.validateCity("New York"));
        assertTrue(ValidadiotData.validateCity("Los Angeles"));
        assertFalse(ValidadiotData.validateCity("Los_Angeles"));
        assertFalse(ValidadiotData.validateCity("New York123"));
    }

    @Test
    public void testValidateStreet() {
        ValidadiotData ValidityData = new ValidadiotData();
        assertTrue(ValidadiotData.validateStreet("Krakowska"));
        assertTrue(ValidadiotData.validateStreet("Krakowska 1"));
        assertTrue(ValidadiotData.validateStreet("-"));
        assertFalse(ValidadiotData.validateStreet("Krakowska1"));
        assertFalse(ValidadiotData.validateStreet("Krakowska 1-"));
    }

    @Test
    public void testValidateStreetNumber() {

        assertTrue(ValidadiotData.validateStreetNumber("1A"));
        assertTrue(ValidadiotData.validateStreetNumber("123"));
        assertFalse(ValidadiotData.validateStreetNumber("1-A"));
        assertFalse(ValidadiotData.validateStreetNumber("A1"));
        assertFalse(ValidadiotData.validateStreetNumber("12345"));
    }

    @Test
    public void testValidatePhoneNumber() {

        assertTrue(ValidadiotData.validatePhoneNumber("1234567"));
        assertTrue(ValidadiotData.validatePhoneNumber("987654321"));
        assertFalse(ValidadiotData.validatePhoneNumber("123-4567"));
        assertFalse(ValidadiotData.validatePhoneNumber("1234567890"));
        assertFalse(ValidadiotData.validatePhoneNumber("abcdefgh"));
    }

    @Test
    public void testValidateDecimalNumber() {
        assertTrue(ValidadiotData.validateDecimalNumber("1234567890"));
        assertTrue(ValidadiotData.validateDecimalNumber("0"));
        assertFalse(ValidadiotData.validateDecimalNumber("1234567890.12"));
        assertFalse(ValidadiotData.validateDecimalNumber("-1234567890"));
        assertFalse(ValidadiotData.validateDecimalNumber("1234567890123"));
    }

    @Test
    public void testValidateDecimalNumberTIRE() {
        assertTrue(ValidadiotData.validateDecimalNumberTIRE("12"));
        assertTrue(ValidadiotData.validateDecimalNumberTIRE("123"));
        assertFalse(ValidadiotData.validateDecimalNumberTIRE("1234"));
        assertFalse(ValidadiotData.validateDecimalNumberTIRE("-12"));
    }

    @Test
    public void testValidateTAG() {
        assertTrue(ValidadiotData.validateTAG("12345678"));
        assertTrue(ValidadiotData.validateTAG("123456789"));
        assertTrue(ValidadiotData.validateTAG("1234567890"));
        assertFalse(ValidadiotData.validateTAG("1234567"));
        assertFalse(ValidadiotData.validateTAG("1234567890a"));
        assertFalse(ValidadiotData.validateTAG("-1234567890"));
    }
}
