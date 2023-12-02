package library.lib.frontend.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final String USERNAME_REGEX = "[a-zA-Z0-9_]+";
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d!@#$%^&*()-_=+\\\\|[{]};:'\",<.>/?`~]{8,}$";

    private Validator() {

    }

    public static boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean validateUsername(String username) {
        return username.matches(USERNAME_REGEX);
    }

    public static boolean validatePassword(String password) {
        return password.matches(PASSWORD_REGEX);
    }
}
