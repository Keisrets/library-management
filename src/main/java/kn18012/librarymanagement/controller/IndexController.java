package kn18012.librarymanagement.controller;

import kn18012.librarymanagement.domain.User;
import kn18012.librarymanagement.service.LoanService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class IndexController {

    private LoanService loanService;

    public IndexController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/")
    public String index(Principal principal, @AuthenticationPrincipal User user, Model model) {
        if(principal != null) model.addAttribute("user", user);
        return "index";
    }

    @GetMapping("/my-loans")
    public String showLoanView(Principal principal, @AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("loans", loanService.findLoansByUser(user));
        return "usr/loans";
    }
}
