package kn18012.librarymanagement.controller;

import kn18012.librarymanagement.domain.User;
import kn18012.librarymanagement.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/lib-admin")
public class AdminController {

    UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String index() {
        return "adm/index";
    }

    @GetMapping("/new-lib")
    public String addLibrarianView(Model model) {
        model.addAttribute("librarian", new User());
        return "adm/new-librarian";
    }

    @PostMapping("/create-lib")
    public String createLibrarian(@ModelAttribute User librarian) {
        userService.saveLibrarian(librarian);
        return "redirect:/lib-admin";
    }
}
