package kn18012.librarymanagement.utility;

import org.springframework.security.core.Authentication;

public class RedirectUrlBuilder {

    public static String newRedirectUrl(Authentication authentication) {
        String role = authentication.getAuthorities().toString();

        if(role.contains("admin"))
            return "redirect:/lib-admin/users/page/1/?phrase=&password_change=success";
        else if(role.contains("librarian"))
            return "redirect:/lib-dashboard/loans/page/1/?phrase=&password_change=success";
        else
            return "redirect:/change-password?password_change=success";
    }
}