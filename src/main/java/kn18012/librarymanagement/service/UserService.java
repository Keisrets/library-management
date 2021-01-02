package kn18012.librarymanagement.service;

import kn18012.librarymanagement.domain.Role;
import kn18012.librarymanagement.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> findAllByRole(Role role);

    List<Role> findAllRoles();

    Page<User> searchForUser(String phrase, int pageNumber);

    User findById(Long id);

    boolean userExists(String email);

    User registerUser(User user);

    User saveUser(User user, ArrayList<Role> roles);

    User update(Long id, User user);

    void deleteById(Long id);
}