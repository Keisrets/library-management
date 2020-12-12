package kn18012.librarymanagement.domain;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

public enum Role implements GrantedAuthority {
    user, librarian, admin;

    @Override
    public String getAuthority() {
        return name();
    }
}
