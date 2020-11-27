package kn18012.librarymanagement.controller;

import kn18012.librarymanagement.domain.User;
import kn18012.librarymanagement.service.UserService;
import kn18012.librarymanagement.service.implementation.UserServiceImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegisterController {

    UserServiceImpl userService;

    public RegisterController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showRegisterForm(WebRequest request, Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

//    @PostMapping
//    public String registerUser(User user) {
//        registerUser(user);
//        return "redirect:/login";
//    }

    @PostMapping
    public String registerUserAccount(@Valid User user, BindingResult errors) {
        try {
            userService.registerUser(user);
        } catch (Exception exception) {
            System.out.println(exception);
        }

        return "login";
    }
}
