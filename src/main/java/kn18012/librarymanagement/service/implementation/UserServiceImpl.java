package kn18012.librarymanagement.service.implementation;

import kn18012.librarymanagement.domain.User;
import kn18012.librarymanagement.repository.RoleRepository;
import kn18012.librarymanagement.repository.UserRepository;
import kn18012.librarymanagement.service.UserService;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User saveLibrarian(User librarian) {
        // password encryption here...
        librarian.setRole(roleRepository.findByRole("librarian"));
        return userRepository.save(librarian);
    }

    @Override
    public User saveUser(User user) {
        user.setRole(roleRepository.findByRole("user"));
        return userRepository.save(user);
    }
}
