package kn18012.librarymanagement.service;

import kn18012.librarymanagement.domain.User;

public interface UserService {

    User saveLibrarian(User user);

    User saveUser(User user);
}