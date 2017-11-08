package ua.com.marinanikitenko.addonchatsignin.utils;

/**
 * Created by Marina on 15.08.2016.
 *
 * Helper for validate email & password fields
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class EmailValidator {
    private Pattern pattern;
    private Matcher matcher;

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public EmailValidator() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    /**
     * Validate hex with regular expression
     *
     * @param hex
     *            hex for validation
     * @return true valid hex, false invalid hex
     */
    public boolean validate(final String hex) {
        matcher = pattern.matcher(hex);
        return matcher.matches();
    }

    /**
     * Validate password by length.password length must >= 6 characters
     *
     * @param password
     *
     * @return true valid password, false invalid password
     */
    public boolean validatePassword(final String password) {
       if(password.length() >= 6){
            return true;
        }
        else {
           return false;
       }
    }
}