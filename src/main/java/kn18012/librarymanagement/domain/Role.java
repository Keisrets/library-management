package kn18012.librarymanagement.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    user, librarian, admin;

    @Override
    public String getAuthority() {
        return name();
    }
}
