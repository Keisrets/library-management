package kn18012.librarymanagement.controller;

import kn18012.librarymanagement.domain.User;
import kn18012.librarymanagement.service.AuthorService;
import kn18012.librarymanagement.service.BookService;
import kn18012.librarymanagement.service.LoanService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/lib-dashboard")
public class LibrarianController {

    private LoanService loanService;
    private BookService bookService;
    private AuthorService authorService;

    public LibrarianController(LoanService loanService, BookService bookService, AuthorService authorService) {
        this.loanService = loanService;
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping
    public String home(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("books", bookService.findAll());
        model.addAttribute("authors", authorService.findAllAuthors());
        model.addAttribute("loans", loanService.findAllLoans());
        return "lib/index";
    }
}