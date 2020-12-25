package kn18012.librarymanagement.controller;

import kn18012.librarymanagement.domain.Loan;
import kn18012.librarymanagement.domain.Role;
import kn18012.librarymanagement.domain.User;
import kn18012.librarymanagement.service.BookService;
import kn18012.librarymanagement.service.LoanService;
import kn18012.librarymanagement.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/lib-dashboard")
public class LoanController {

    private BookService bookService;
    private UserService userService;
    private LoanService loanService;

    public LoanController(BookService bookService, UserService userService, LoanService loanService) {
        this.bookService = bookService;
        this.userService = userService;
        this.loanService = loanService;
    }

    @GetMapping("/loans/page/{pageNumber}")
    public String showLoanSearchResults(@AuthenticationPrincipal User user, @PathVariable("pageNumber") int pageNumber, @RequestParam("phrase") String phrase, Model model) {
        Page<Loan> page = loanService.searchForLoan(phrase, pageNumber);
        List<Loan> loans = page.getContent();

        model.addAttribute("user", user);
        model.addAttribute("loans", loans);
        model.addAttribute("phrase", phrase);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page.getTotalPages());
        return "lib/index";
    }

    @GetMapping("/new-loan")
    public String addBookView(@AuthenticationPrincipal User user, Model model) {
        Role userRole = Role.user;
        model.addAttribute("user", user);
        model.addAttribute("loan", new Loan());
        model.addAttribute("allBooks", bookService.findAll());
        model.addAttribute("allUsers", userService.findAllByRole(userRole));
        return "lib/new-loan";
    }

    @PostMapping("/create-loan")
    public String createBook(@ModelAttribute Loan loan, @RequestParam("date") String end_date) {
        LocalDate endDate = LocalDate.parse(end_date);
        loan.setEnd_date(endDate);
        loanService.saveLoan(loan);
        return "redirect:/lib-dashboard";
    }

    @RequestMapping("/update-loan/{loanId}")
    public String update(@PathVariable("loanId") Long id) {
        loanService.updateLoan(id);
        return "redirect:/lib-dashboard";
    }

    @DeleteMapping
    @RequestMapping("/delete-loan/{loanId}")
    public String delete(@PathVariable("loanId") Long id) {
        loanService.deleteLoan(id);
        return "redirect:/lib-dashboard";
    }
}
