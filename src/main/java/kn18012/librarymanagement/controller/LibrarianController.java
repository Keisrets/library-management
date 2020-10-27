package kn18012.librarymanagement.controller;

import kn18012.librarymanagement.domain.Author;
import kn18012.librarymanagement.domain.Book;
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

    public LibrarianController(LibrarianService librarianService, BookService bookService) {
        this.librarianService = librarianService;
        this.bookService = bookService;
    }

    @GetMapping
    public String home(Model model) {
        model.addAttribute("books", bookService.findAll());
        model.addAttribute("authors", bookService.findAllAuthors());
        return "lib/index";
    }

    @GetMapping("/profile")
    public String viewProfile() {
        return "";
    }

    @GetMapping("/new-book")
    public String addBookView(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("allAuthors", bookService.findAllAuthors());
        return "lib/new-book";
    }

    // post book add form
    @PostMapping("/create-book")
    public String create(@ModelAttribute Book book) {
        librarianService.save(book);
        return "redirect:/lib/new-book";
    }

    @GetMapping("/edit-book/{bookId}")
    public String editBookView(@PathVariable("bookId") Long bookId, Model model) {
        model.addAttribute("book", bookService.findById(bookId));
        model.addAttribute("allAuthors", bookService.findAllAuthors());
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

    @GetMapping("/update-author/{authorId}")
    public String editAuthorView() {
        return "";
    }

    @DeleteMapping
    @RequestMapping("/delete-author/{authorId}")
    public String deleteAuthor(@PathVariable("authorId") Long authorId) {
        librarianService.deleteAuthorById(authorId);
        return "redirect:/lib";
    }

}
