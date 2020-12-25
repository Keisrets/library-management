package kn18012.librarymanagement.utility;

import org.springframework.security.core.Authentication;

public class RedirectUrlBuilder {

    public static String newRedirectUrl(Authentication authentication) {
        String role = authentication.getAuthorities().toString();

        if(role.contains("admin"))
            return "redirect:/lib-admin";
        else if(role.contains("librarian"))
            return "redirect:/lib-dashboard";
        else
            return "redirect:/";
    }
}
