package kn18012.librarymanagement.service;

import kn18012.librarymanagement.domain.Role;
import kn18012.librarymanagement.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> findAll();

    List<User> findAllByRole(Role role);

    List<Role> findAllRoles();

    User findById(Long id);

    boolean userExists(String email);

    User registerUser(User user);

    User saveUser(User user, ArrayList<Role> roles);

    User updateUser(Long id, User user);

    void deleteById(Long id);
}