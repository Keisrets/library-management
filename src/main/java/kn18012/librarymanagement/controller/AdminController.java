package kn18012.librarymanagement.controller;

import kn18012.librarymanagement.domain.User;
import kn18012.librarymanagement.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/lib-admin")
public class AdminController {

    private UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("users", userService.findAll());
        return "adm/index";
    }

    @GetMapping("/new-user")
    public String addLibrarianView(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", userService.findAllRoles());
        return "adm/new-user";
    }

    @PostMapping("/create-user")
    public String createLibrarian(@ModelAttribute User user) {
        userService.saveUser(user, user.getRole().getRole());
        return "redirect:/lib-admin";
    }

    @GetMapping("/edit-user/{userId}")
    public String editLibrarianView(@PathVariable("userId") Long id, Model model) {
        model.addAttribute("roles", userService.findAllRoles());
        model.addAttribute("user", userService.findById(id));
        return "adm/edit-user";
    }

    @PostMapping("/update-user/{userId}")
    public String update(@PathVariable("userId") Long id, @ModelAttribute User user, @ModelAttribute String pw) {
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