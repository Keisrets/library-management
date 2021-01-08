package kn18012.librarymanagement.controller;

import kn18012.librarymanagement.domain.Book;
import kn18012.librarymanagement.domain.User;
import kn18012.librarymanagement.service.BookService;
import kn18012.librarymanagement.service.LoanService;
import kn18012.librarymanagement.service.UserService;
import kn18012.librarymanagement.utility.ErrorMessage;
import kn18012.librarymanagement.utility.PasswordCheck;
import kn18012.librarymanagement.utility.RedirectUrlBuilder;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
public class IndexController {

    private LoanService loanService;
    private UserService userService;
    private BookService bookService;

    public IndexController(LoanService loanService, UserService userService, BookService bookService) {
        this.loanService = loanService;
        this.userService = userService;
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String index(Principal principal, @AuthenticationPrincipal User user, Model model) {
        // if user logged in, add user data to templates
        if(principal != null) model.addAttribute("user", user);
        return "index";
    }

    @GetMapping("/my-loans")
    public String userLoansView(@AuthenticationPrincipal User user, Model model) {
        // add user and loan data to model
        model.addAttribute("user", user);
        model.addAttribute("loans", loanService.findLoansByUser(user));
        return "usr/loans";
    }

    @GetMapping("/change-password")
    public String showChangePasswordView() {
        // add a temporary user to model
        // model.addAttribute("tempUser", new User());
        // model.addAttribute("user", user);
        return "usr/change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@AuthenticationPrincipal User user, @RequestParam("pass1") String newPassword, @RequestParam("pass2") String newPasswordConfirm, Model model) {
        // check if both passwords match
        String message = PasswordCheck.PasswordValidate(newPassword, newPasswordConfirm);
        if(!message.equals("ok")) {
            model.addAttribute("error", new ErrorMessage(message));
            return "usr/change-password";
        }
        // update user with the new password
        user.setPassword(newPassword);
        userService.update(user.getId(), user);
        // redirect user based on role
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return RedirectUrlBuilder.newRedirectUrl(auth);
    }

    @GetMapping("/results/page/{pageNumber}")
    public String pagedSearchResults(@PathVariable("pageNumber") int pageNumber, @RequestParam("phrase") String phrase, Model model) {
        // create a new page with book search results
        Page<Book> page = bookService.searchForBook(phrase, pageNumber);
        // get page content
        List<Book> books = page.getContent();
        // add attributes to template
        model.addAttribute("books", books);
        model.addAttribute("phrase", phrase);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page.getTotalPages());
        return "search-results";
    }

    @GetMapping("/book/{bookId}")
    public String singleBookView(@AuthenticationPrincipal User user, @PathVariable("bookId") Long bookId, Model model) {
        // get data for single book template
        Book theBook = bookService.findById(bookId);
        model.addAttribute("book", theBook);
        model.addAttribute("user", user);
        return "single-book";
    }
}