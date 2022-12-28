package org.main.Utils;



import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidadiotData {
    private static final String REGEX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,30}$";
    private static final String REGEX_NAME = "[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ ]{3,20}$";
    private static final String REGEX_ZIPCODE = "\\d{2}(-\\d{3})?";
    private static final String REGEX_STREET_SHORT = "[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ]{3,20}$";
    private static final String REGEX_STREET_MISSING = "(-)";
    private static final String REGEX_STREET_LONG = "^.*?\\s[N]{0,1}([A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ0-9]+)\\s*\\w*$";
    private static final String REGEX_PHONE_NUMBER = "[0-9]{6,9}$";
    private static final String REGEX_NUMBERS_DECIMAL = "[0-9]{1,12}$";
    private static final String REGEX_NUMBERS_DECIMAL_TIRE = "[0-9]{2,3}$";
    private static final String REGEX_NUMBERS_TAG = "[0-9]{8,10}$";
    private static final String REGEX_DATE = "^[0-3]?[0-9].[0-3]?[0-9].(?:[0-9]{2})?[0-9]{2}$";
    private static final String REGEX_STREET_NUMBER = "[A-Za-z0-9]{1,5}$";
    private static final String REGEX_ACCOUNT_NUMBER = "\\d{2}[ ]\\d{4}[ ]\\d{4}[ ]\\d{4}[ ]\\d{4}[ ]\\d{4}[ ]\\d{4}";
    private static final String REGEX_BANK_NAME = "[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ ]{3,40}$";
    private static final String REGEX_MODEL_MARKA = "[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ0-9 ]{2,50}$";
    private static final String REGEX_NIP_COMPANY = "\\d{3}[-]\\d{2}[-]\\d{2}[-]\\d{3}";
    private static final String REGEX_NIP_PERSON = "\\d{3}[-]\\d{3}[-]\\d{2}[-]\\d{2}";

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    /**
     *  Vaidate emial
     * @return true is valid
     *   zwraza prwade jesi spełnia warunki
     */
    public static boolean validdateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
    public static boolean validateDate(String date) {
        if (Pattern.matches(REGEX_DATE, date) ) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * Sprawdzenie poprawnośici hasłą
     * chasło i potwiedzenie muszą być identyczne
     * zwraza prwade jesi spełnia warunki
     */
    public static boolean validatePassword(String pass, String confirmPass) {
        if (Pattern.matches(REGEX_PASSWORD, pass) && pass.equals(confirmPass)) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * Sprawdzenie poprawnośici hasłą
     * chasło i potwiedzenie muszą być identyczne
     * zwraza prwade jesi spełnia warunki
     */
    public static boolean validatePassword(String pass) {
        if (Pattern.matches(REGEX_PASSWORD, pass)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Sprawdzenie poprawnośici imienia nazwiska
     * imię i nazwisko ogą sklłądać się tylko z liter
     * zwraza prwade jesi spełnia warunki
     */
    public static boolean valideteName(String text) {
        if (Pattern.matches(REGEX_NAME, text)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *   Sprawdzenie poprawnośici kodu pocztowego
     *    00-000
     *   zwraza prwade jesi spełnia warunki
     */
    public static boolean validateZipCode(String zipCode) {
        if (zipCode.matches(REGEX_ZIPCODE)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *   Sprawdzenie poprawnośici kodu pocztowego
     *    00-000
     *   zwraza prwade jesi spełnia warunki
     */
    public static boolean validateCity(String city) {
        if (Pattern.matches(REGEX_NAME, city)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *   Sprawdzenie poprawnośici ulicy
     *   sprawdzanie krótkich ulic długich jeśli brak to spradza czy jest -
     *   zwraza prwade jesi spełnia warunki
     */
    public static boolean validateStreet(String street) {
        if (Pattern.matches(REGEX_STREET_SHORT, street)
                || Pattern.matches(REGEX_STREET_LONG, street)
                || Pattern.matches(REGEX_STREET_MISSING, street)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *   Sprawdzenie poprawnośici numeru domu
     *   adres może składać się z liter i cyfr
     *   zwraza prwade jesi spełnia warunki
     */
    public static boolean validateStreetNumber(String streetNumber) {
        if (streetNumber.matches(REGEX_STREET_NUMBER)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *   Sprawdzenie poprawnośici numeru komurkowego
     *   numer skałada się tylko z cyfr
     *   zwraza prwade jesi spełnia warunki
     */
    public static boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber.matches(REGEX_PHONE_NUMBER)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validateDecimalNumber(String number) {
        if (number.matches(REGEX_NUMBERS_DECIMAL)) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean validateDecimalNumberTIRE(String number) {
        if (number.matches(REGEX_NUMBERS_DECIMAL_TIRE)) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean validateTAG(String number) {
        if (number.matches(REGEX_NUMBERS_TAG)) {
            return true;
        } else {
            return false;
        }
    }

    //11 1111 1111 1111 1111 1111 1111 valid format
    public static boolean validateAcountNumber(String number) {
        if (number.matches(REGEX_ACCOUNT_NUMBER)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validateBankName(String name) {
        if (name.matches(REGEX_BANK_NAME)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validateMarkaAndModel(String name) {
        if (name.matches(REGEX_MODEL_MARKA)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validateNIPCompany(String name) {
        if (name.matches(REGEX_NIP_COMPANY)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validateNIPPerson(String name) {
        if (name.matches(REGEX_NIP_PERSON)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNipValid(String nip) {
        if (nip.length() == 13) {
            nip = nip.replaceAll("-", "");
        }
        if (nip.length() != 10) {
            return false;
        }
        int[] weights = {6, 5, 7, 2, 3, 4, 5, 6, 7};
        try {
            int sum = 0;
            for (int i = 0; i < weights.length; ++i) {
                sum += Integer.parseInt(nip.substring(i, i + 1)) * weights[i];
            }
            return (sum % 11) == Integer.parseInt(nip.substring(9, 10));
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
