package kn18012.librarymanagement.service.implementation;

import kn18012.librarymanagement.domain.Role;
import kn18012.librarymanagement.domain.User;
import kn18012.librarymanagement.repository.UserRepository;
import kn18012.librarymanagement.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        // encrypt password using bcrypt
        final String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        ArrayList<Role> roles = new ArrayList<>();
        // set user role to new user
        roles.add(Role.user);
        user.setRoles(roles);

        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return user.get();
        }
        else {
            throw new UsernameNotFoundException("User with that email not found");
        }
    }

    @Override
    public boolean userExists(String email) {
        User existingUser = userRepository.findByEmail(email).orElse(null);

        if(existingUser != null) {
            return true;
        } else {
            return false;
        }
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> searchForUser(String phrase, int pageNumber) {
        // set how many results are displayed per page
        Pageable pageable = PageRequest.of(pageNumber - 1, 30);
        // return user search results
        return userRepository.findByEmailIgnoreCaseContainingOrFirstNameIgnoreCaseContainingOrLastNameIgnoreCaseContaining(phrase, phrase, phrase, pageable);
    }

    public List<User> findAllByRole(Role role) {
        return userRepository.findUserByRolesContaining(role);
    }

    public List<Role> findAllRoles() {
        List<Role> allRoles = Arrays.asList(Role.values());
        return allRoles;
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User saveUser(User user, List<Role> roles) {
        // set user password and role
        final String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        user.setRoles(roles);

        return userRepository.save(user);
    }

    public User update(Long id, User user) {
        User userToUpdate = userRepository.findById(id).orElse(null);
        // encrypt new password string
        final String newEncryptedPassword = passwordEncoder.encode(user.getPassword());
        // update user fields with new values
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPassword(newEncryptedPassword);
        userToUpdate.setRoles(user.getRoles());
        userToUpdate.setLoans(user.getLoans());

        return userRepository.save(userToUpdate);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}