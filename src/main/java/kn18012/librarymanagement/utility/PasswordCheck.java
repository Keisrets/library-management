package kn18012.librarymanagement.utility;

public class PasswordCheck {

    public static String PasswordValidate(String newPassword, String newPasswordConfirm) {

        if(!newPassword.equals(newPasswordConfirm))
            return "Passwords don't match!";

        if(newPassword.length() < 6)
            return "Password must be at least 6 characters long!";

        return "ok";
    }
}