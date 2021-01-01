package kn18012.librarymanagement.controller;

import kn18012.librarymanagement.domain.Book;
import kn18012.librarymanagement.domain.User;
import kn18012.librarymanagement.service.AuthorService;
import kn18012.librarymanagement.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/lib-dashboard")
public class BookController {

    private AuthorService authorService;
    private BookService bookService;

    public BookController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @GetMapping("/books-list/page/{pageNumber}")
    public String pagedBookView(@AuthenticationPrincipal User user, @PathVariable("pageNumber") int pageNumber, @RequestParam("phrase") String phrase, Model model) {
        Page<Book> page = bookService.searchForBook(phrase, pageNumber);
        List<Book> books = page.getContent();

        model.addAttribute("user", user);
        model.addAttribute("books", books);
        model.addAttribute("phrase", phrase);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page.getTotalPages());

        return "lib/book-list";
    }

    @GetMapping("/new-book")
    public String addBookView(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("book", new Book());
        model.addAttribute("allAuthors", authorService.findAllAuthors());
        model.addAttribute("genres", bookService.findAllGenres());
        return "lib/new-book";
    }

    @PostMapping("/create-book")
    public String createBook(@Valid @ModelAttribute Book book, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "lib/new-book";
        }

        bookService.save(book);
        return "redirect:/lib-dashboard/books-list/page/1/?phrase=&book_create=success";
    }

    @GetMapping("/edit-book/{bookId}")
    public String editBookView(@AuthenticationPrincipal User user, @PathVariable("bookId") Long id, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("book", bookService.findById(id));
        model.addAttribute("allAuthors", authorService.findAllAuthors());
        model.addAttribute("genres", bookService.findAllGenres());
        return "lib/edit-book";
    }

    @PostMapping("/update-book/{bookId}")
    public String updateBook(@PathVariable("bookId") Long id, @Valid @ModelAttribute Book book, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "lib/new-book";
        }

        bookService.update(id, book);
        return "redirect:/lib-dashboard/books-list/page/1/?phrase=&book_update=success";
    }

    @DeleteMapping
    @RequestMapping("/delete-book/{bookId}")
    public String deleteBook(@PathVariable("bookId") Long bookId) {
        bookService.deleteBookById(bookId);
        return "redirect:/lib-dashboard/books-list/page/1/?phrase=&book_delete=success";
    }
}
