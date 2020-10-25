package kn18012.librarymanagement.service;

import kn18012.librarymanagement.domain.Role;
import kn18012.librarymanagement.domain.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    List<Role> findAllRoles();

    User findById(Long id);

    User saveUser(User user, String role);

    User updateUser(Long id, User user);

    void deleteById(Long id);
}