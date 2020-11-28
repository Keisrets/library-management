package kn18012.librarymanagement.controller;

import kn18012.librarymanagement.domain.Loan;
import kn18012.librarymanagement.service.BookService;
import kn18012.librarymanagement.service.LibrarianService;
import kn18012.librarymanagement.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@Controller
@RequestMapping("/lib-dashboard")
public class LoanController {

    private BookService bookService;
    private UserService userService;
    private LibrarianService librarianService;

    public LoanController(BookService bookService, UserService userService, LibrarianService librarianService) {
        this.bookService = bookService;
        this.userService = userService;
        this.librarianService = librarianService;
    }

    @GetMapping("/new-loan")
    public String addBookView(Model model) {
        model.addAttribute("loan", new Loan());
        model.addAttribute("allBooks", bookService.findAll());
        model.addAttribute("allUsers", userService.findAllByRole("user"));
        return "lib/new-loan";
    }

    @PostMapping("/create-loan")
    public String createBook(@ModelAttribute Loan loan, @RequestParam("date") String end_date) {
        LocalDate endDate = LocalDate.parse(end_date);
        loan.setEnd_date(endDate);
        librarianService.saveLoan(loan);
        return "redirect:/lib-dashboard";
    }

}
