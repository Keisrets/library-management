package kn18012.librarymanagement.controller;

import kn18012.librarymanagement.domain.User;
import kn18012.librarymanagement.repository.LoanRepository;
import kn18012.librarymanagement.service.LoanService;
import kn18012.librarymanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private LoanService loanService;

    @Autowired
    LoanRepository loanRepository;

    public IndexController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/")
    public String index() {
        return ("index");
    }

    @GetMapping("/my-loans")
    public String showLoanView(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("loans", loanRepository.findLoansByUser(user));
        return "usr/loans";
    }
}
