package kn18012.librarymanagement.controller;

import kn18012.librarymanagement.domain.Author;
import kn18012.librarymanagement.domain.Book;
import kn18012.librarymanagement.service.AuthorService;
import kn18012.librarymanagement.service.BookService;
import kn18012.librarymanagement.service.LibrarianService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/lib")
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

    @GetMapping("/new-book")
    public String addBookView(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("allAuthors", authorService.findAllAuthors());
        return "lib/new-book";
    }

    @PostMapping("/create-book")
    public String create(@ModelAttribute Book book) {
        librarianService.save(book);
        return "redirect:/lib/new-book";
    }

    @GetMapping("/edit-book/{bookId}")
    public String editBookView(@PathVariable("bookId") Long bookId, Model model) {
        model.addAttribute("book", bookService.findById(bookId));
        model.addAttribute("allAuthors", authorService.findAllAuthors());
        return "lib/edit-book";
    }

    @PostMapping("/update-book/{bookId}")
    public String update(@PathVariable("bookId") Long bookId, @ModelAttribute Book book) {
        librarianService.update(bookId, book);
        return "redirect:/lib";
    }

    @DeleteMapping
    @RequestMapping("/del/{bookId}")
    public String delete(@PathVariable("bookId") Long bookId) {
        librarianService.deleteBookById(bookId);
        return "redirect:/lib";
    }

    @GetMapping("/new-author")
    public String addAuthorView(Model model) {
        model.addAttribute("author", new Author());
        return "lib/new-author";
    }

    @PostMapping("/create-author")
    public String createAuthor(@ModelAttribute Author author) {
        librarianService.save(author);
        return "redirect:/lib";
    }

    @GetMapping("/edit-author/{authorId}")
    public String editAuthorView(@PathVariable("authorId") Long authorId, Model model) {
        model.addAttribute("author", authorService.findAuthorById(authorId));
        return "lib/edit-author";
    }

    @PostMapping("/update-author/{authorId}")
    public String editAuthorView(@PathVariable("authorId") Long authorId, @ModelAttribute Author author) {
        librarianService.update(authorId, author);
        return "redirect:/lib";
    }

    @DeleteMapping
    @RequestMapping("/delete-author/{authorId}")
    public String deleteAuthor(@PathVariable("authorId") Long authorId) {
        librarianService.deleteAuthorById(authorId);
        return "redirect:/lib";
    }

}
