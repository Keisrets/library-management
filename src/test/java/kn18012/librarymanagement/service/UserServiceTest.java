package kn18012.librarymanagement.service;

import kn18012.librarymanagement.domain.*;
import kn18012.librarymanagement.repository.UserRepository;
import kn18012.librarymanagement.service.implementation.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    private User u1;
    private User l1;
    private User a1;
    private List<User> userList = new ArrayList<>();
    private List<User> adminList = new ArrayList<>();
    private List<User> librarianList = new ArrayList<>();
    private List<Role> userRole = new ArrayList<>();
    private List<Role> librarianRole = new ArrayList<>();
    private List<Role> adminRole = new ArrayList<>();

    @BeforeEach
    void setUp() {
        u1 = new User(1L, "user", "1", "u1@test.lv", "pass123", userRole, new HashSet<>());
        l1 = new User(2L, "librarian", "1", "l1@test.lv", "123pass", librarianRole, new HashSet<>());
        a1 = new User(3L, "admin", "1", "a1@test.lv", "pass123", adminRole, new HashSet<>());

        userList.add(u1);
        librarianList.add(l1);
        adminList.add(a1);

        userRole.add(Role.user);
        librarianRole.add(Role.librarian);
        adminRole.add(Role.admin);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findAllByRole() {
        when(userRepository.findUserByRolesContaining(Role.user)).thenReturn(userList);
        when(userRepository.findUserByRolesContaining(Role.librarian)).thenReturn(librarianList);
        when(userRepository.findUserByRolesContaining(Role.admin)).thenReturn(adminList);

        List<User> result1 = userService.findAllByRole(Role.user);
        List<Role> result1Role = result1.get(0).getRoles();
        List<User> result2 = userService.findAllByRole(Role.librarian);
        List<Role> result2Role = result2.get(0).getRoles();
        List<User> result3 = userService.findAllByRole(Role.admin);
        List<Role> result3Role = result3.get(0).getRoles();

        verify(userRepository, times(3)).findUserByRolesContaining(any(Role.class));
        assertEquals(result1.get(0).getId(), u1.getId());
        assertEquals(result1Role.get(0), Role.user);

        assertEquals(result2.get(0).getId(), l1.getId());
        assertEquals(result2Role.get(0), Role.librarian);

        assertEquals(result3.get(0).getId(), a1.getId());
        assertEquals(result3Role.get(0), Role.admin);
    }

    @Test
    void findAllRoles() {
        List<Role> roles = userService.findAllRoles();
        assertNotNull(roles);
        assertEquals(3, roles.size());
    }

    @Test
    void findById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(u1));
        when(userRepository.findById(2L)).thenReturn(Optional.of(l1));
        when(userRepository.findById(3L)).thenReturn(Optional.of(a1));

        User result1 = userService.findById(1L);
        User result2 = userService.findById(2L);
        User result3 = userService.findById(3L);

        verify(userRepository, times(3)).findById(anyLong());
        assertEquals(1L, result1.getId());
        assertEquals(2L, result2.getId());
        assertEquals(3L, result3.getId());
    }

    @Test
    void userExists() {
        when(userRepository.findByEmail("u1@test.lv")).thenReturn(Optional.of(u1));

        boolean result1 = userService.userExists("test@mail.lv");
        boolean result2 = userService.userExists("u1@test.lv");

        verify(userRepository, times(2)).findByEmail(anyString());
        assertFalse(result1);
        assertTrue(result2);
    }

    @Test
    void registerUser() {
        when(userRepository.save(u1)).thenReturn(u1);
        User result = userService.registerUser(u1);
        verify(userRepository, times(1)).save(any(User.class));
        assertEquals(userRole.get(0), result.getRoles().get(0));
        assertEquals(u1, result);
    }

    @Test
    void saveUser() {
        when(userRepository.save(u1)).thenReturn(u1);
        when(userRepository.save(l1)).thenReturn(l1);
        when(userRepository.save(a1)).thenReturn(a1);

        User result1 = userService.saveUser(u1, librarianRole);
        User result2 = userService.saveUser(l1, adminRole);
        User result3 = userService.saveUser(a1, userRole);

        verify(userRepository, times(3)).save(any(User.class));
        assertEquals(u1, result1);
        assertEquals(l1, result2);
        assertEquals(a1, result3);
        assertEquals(librarianRole, result1.getRoles());
        assertEquals(adminRole, result2.getRoles());
        assertEquals(userRole, result3.getRoles());
    }

    @Test
    void update() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(u1));
        when(userRepository.save(any(User.class))).thenReturn(u1);
        User result = userService.update(1L, l1);

        assertEquals(1L, result.getId());
        assertEquals(librarianRole, result.getRoles());
        assertEquals("l1@test.lv", result.getEmail());
    }

    @Test
    void deleteById() {
        userService.deleteById(1L);
        verify(userRepository, atMostOnce()).deleteById(1L);
    }
}