package kn18012.librarymanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/lib-dashboard")
public class LibrarianController {

    @GetMapping
    public String home() {
        return "redirect:/lib-dashboard/loans/page/1/?phrase=";
    }
}