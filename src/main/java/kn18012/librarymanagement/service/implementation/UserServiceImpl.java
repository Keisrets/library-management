package kn18012.librarymanagement.service.implementation;

import kn18012.librarymanagement.domain.Role;
import kn18012.librarymanagement.domain.User;
import kn18012.librarymanagement.repository.RoleRepository;
import kn18012.librarymanagement.repository.UserRepository;
import kn18012.librarymanagement.service.UserService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User saveUser(User user, String role) {
        // password encryption here...
        user.setRole(roleRepository.findByRole(role));
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User user) {
        // need to handle exception if user not found.
        User update = userRepository.findById(id).orElse(null);
        Role newRole = roleRepository.findByRole(user.getRole().getRole());

        update.setFirstName(user.getFirstName());
        update.setLastName(user.getLastName());
        update.setEmail(user.getEmail());
        update.setPassword(user.getPassword());
        update.setRole(newRole);
        update.setLoans(user.getLoans());

        return userRepository.save(update);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
