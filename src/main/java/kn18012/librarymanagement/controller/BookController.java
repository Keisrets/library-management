package kn18012.librarymanagement.controller;

import kn18012.librarymanagement.domain.Book;
import kn18012.librarymanagement.service.AuthorService;
import kn18012.librarymanagement.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/lib-dashboard")
public class BookController {

    private AuthorService authorService;
    private BookService bookService;

    public BookController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @GetMapping("/new-book")
    public String addBookView(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("allAuthors", authorService.findAllAuthors());
        return "lib/new-book";
    }

    @PostMapping("/create-book")
    public String createBook(@ModelAttribute Book book) {
        bookService.save(book);
        return "redirect:/lib-dashboard";
    }

    @GetMapping("/edit-book/{bookId}")
    public String editBookView(@PathVariable("bookId") Long id, Model model) {
        model.addAttribute("book", bookService.findById(id));
        model.addAttribute("allAuthors", authorService.findAllAuthors());
        return "lib/edit-book";
    }

    @PostMapping("/update-book/{bookId}")
    public String update(@PathVariable("bookId") Long id, @ModelAttribute Book book) {
        bookService.update(id, book);
        return "redirect:/lib-dashboard";
    }

    @DeleteMapping
    @RequestMapping("/del/{bookId}")
    public String delete(@PathVariable("bookId") Long bookId) {
        bookService.deleteBookById(bookId);
        return "redirect:/lib-dashboard";
    }
}
