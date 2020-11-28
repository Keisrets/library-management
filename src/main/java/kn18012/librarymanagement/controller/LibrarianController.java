package kn18012.librarymanagement.controller;

import kn18012.librarymanagement.service.AuthorService;
import kn18012.librarymanagement.service.BookService;
import kn18012.librarymanagement.service.LibrarianService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/lib-dashboard")
public class LibrarianController {

    private LibrarianService librarianService;
    private BookService bookService;
    private AuthorService authorService;

    public LibrarianController(LibrarianService librarianService, BookService bookService, AuthorService authorService) {
        this.librarianService = librarianService;
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping
    public String home(Model model) {
        model.addAttribute("books", bookService.findAll());
        model.addAttribute("authors", authorService.findAllAuthors());
        return "lib/index";
    }

    @GetMapping("/profile")
    public String viewProfile() {
        return "";
    }

}