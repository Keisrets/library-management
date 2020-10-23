package kn18012.librarymanagement.controller;

import kn18012.librarymanagement.domain.User;
import kn18012.librarymanagement.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/lib-admin")
public class AdminController {

    UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("users", userService.findAll());
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

    @GetMapping("/edit-lib/{libId}")
    public String librarianEditView(@PathVariable("libId") Long id, Model model) {
        model.addAttribute("librarian", userService.findById(id));
        return "adm/edit-user";
    }

    @PostMapping("/update-lib/{libId}")
    public String update(@PathVariable("libId") Long id, @ModelAttribute User user) {
        userService.updateLibrarian(id, user);
        return "redirect:/lib-admin";
    }

    @DeleteMapping
    @RequestMapping("/delete-lib/{librarianId}")
    public String delete(@PathVariable("librarianId") Long id) {
        userService.deleteById(id);
        return "redirect:/lib-admin";
    }

}