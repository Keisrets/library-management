package kn18012.librarymanagement.controller;

import kn18012.librarymanagement.domain.Role;
import kn18012.librarymanagement.domain.User;
import kn18012.librarymanagement.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@RequestMapping("/lib-admin")
public class AdminController {

    private UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String index(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("user", user);
        return "adm/index";
    }

    @GetMapping("/new-user")
    public String addUserView(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("newUser", new User());
        model.addAttribute("roles", userService.findAllRoles());
        return "adm/new-user";
    }

    @PostMapping("/create-user")
    public String createUser(@Valid @ModelAttribute User user, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "adm/new-user";
        } else {
            if(userService.userExists(user.getEmail())) {
                bindingResult.rejectValue("email", "error.user", "E-mail already taken!");
                return "adm/new-user";
            }
        }

        ArrayList<Role> roles = (ArrayList<Role>) user.getRoles();
        userService.saveUser(user, roles);
        return "redirect:/lib-admin";
    }

    @GetMapping("/edit-user/{userId}")
    public String editLibrarianView(@AuthenticationPrincipal User user, @PathVariable("userId") Long id, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("userToEdit", userService.findById(id));
        return "adm/edit-user";
    }

    @PostMapping("/update-user/{userId}")
    public String update(@PathVariable("userId") Long id, @Valid @ModelAttribute User user, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "adm/edit-user";
        }

        userService.updateUser(id, user);
        return "redirect:/lib-admin";
    }

    @DeleteMapping
    @RequestMapping("/delete-user/{userId}")
    public String delete(@PathVariable("userId") Long id) {
        userService.deleteById(id);
        return "redirect:/lib-admin";
    }
}