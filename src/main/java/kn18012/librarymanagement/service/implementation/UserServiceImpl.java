package kn18012.librarymanagement.service.implementation;

import kn18012.librarymanagement.domain.Role;
import kn18012.librarymanagement.domain.User;
import kn18012.librarymanagement.repository.RoleRepository;
import kn18012.librarymanagement.repository.UserRepository;
import kn18012.librarymanagement.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user){
        final String encryptedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encryptedPassword);
        Role role = roleRepository.findByRole("admin");

        user.setRole(role);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        final Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            return (UserDetails) user.get();
        }
        else {
            throw new UsernameNotFoundException("User with that email not found");
        }
    }



    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findAllByRole(String role) {
        Role userRole = roleRepository.findByRole(role);
        return userRepository.findByRole(userRole);
    }

    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User saveUser(User user, String role) {
        user.setRole(roleRepository.findByRole(role));

        final String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        return userRepository.save(user);
    }

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

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
