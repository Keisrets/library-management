package kn18012.librarymanagement.controller;

import kn18012.librarymanagement.domain.User;
import kn18012.librarymanagement.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping
    public String registerNewUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        // validate user input
        if(bindingResult.hasErrors()) {
            return "register";
        } else {
            // check if user with provided email already exists
            if(userService.userExists(user.getEmail())) {
                bindingResult.rejectValue("email", "error.user", "E-mail already taken!");
                return "register";
            }
        }

        userService.registerUser(user);
        return "redirect:/login?registration=success";
    }
}