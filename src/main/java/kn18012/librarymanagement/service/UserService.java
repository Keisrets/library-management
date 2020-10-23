package kn18012.librarymanagement.service;

import kn18012.librarymanagement.domain.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(Long id);

    User saveLibrarian(User user);

    User saveUser(User user);

    User updateLibrarian(Long id, User user);

    User updateUser(Long id, User user);

    void deleteById(Long id);
}