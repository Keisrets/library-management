package kn18012.librarymanagement.controller;

import kn18012.librarymanagement.domain.Loan;
import kn18012.librarymanagement.domain.Role;
import kn18012.librarymanagement.service.BookService;
import kn18012.librarymanagement.service.LoanService;
import kn18012.librarymanagement.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/lib-dashboard")
public class LibrarianController {

    private BookService bookService;
    private UserService userService;
    private LoanService loanService;

    public LibrarianController(BookService bookService, UserService userService, LoanService loanService) {
        this.bookService = bookService;
        this.userService = userService;
        this.loanService = loanService;
    }

    @GetMapping
    public String index() {
        return "redirect:/lib-dashboard/loans/page/1/?phrase=";
    }

    @GetMapping("/loans/page/{pageNumber}")
    public String pagedLoanView(@PathVariable("pageNumber") int pageNumber, @RequestParam("phrase") String phrase, Model model) {
        // find loans by search phrase
        Page<Loan> page = loanService.searchForLoan(phrase, pageNumber);
        // get page results as list
        List<Loan> loans = page.getContent();

        // add attributes to model
        model.addAttribute("loans", loans);
        model.addAttribute("phrase", phrase);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page.getTotalPages());
        return "lib/index";
    }

    @GetMapping("/new-loan")
    public String addLoanView(Model model) {
        // add attributes to model
        Role userRole = Role.user;
        model.addAttribute("loan", new Loan());
        model.addAttribute("allBooks", bookService.findAll());
        model.addAttribute("allUsers", userService.findAllByRole(userRole));
        return "lib/new-loan";
    }

    @PostMapping("/create-loan")
    public String createLoan(@ModelAttribute Loan loan, @RequestParam("date") String end_date, Model model) {
        String userMessage = "";
        String bookMessage = "";
        String dateMessage = "";

        // user error handling
        if(loan.getUser() == null) userMessage = "Select a user!";
        else if (userService.findById(loan.getUser().getId()) == null) userMessage = "Select a user!";

        // book error handling handling
        if(loan.getBook() == null) bookMessage = "Select a book!";
        else if (bookService.findById(loan.getBook().getId()) == null) bookMessage = "Book not found!";

        // date error handling
        if(end_date == "") {
            dateMessage = "Invalid date!";
        } else {
            // parse date input string to date object
            LocalDate endDate = LocalDate.parse(end_date);
            LocalDate dateNow = LocalDate.now();
            // compare end date to start date, create error message if end
            // date is smaller than start date
            if(endDate.compareTo(dateNow) <= 0) dateMessage = "Invalid date!";
            else loan.setEnd_date(endDate);
        }

        // if any errors,
        if(userMessage != "" || bookMessage != "" || dateMessage != "" ) {
            model.addAttribute("allBooks", bookService.findAll());
            model.addAttribute("allUsers", userService.findAllByRole(Role.user));
            model.addAttribute("error_user", userMessage);
            model.addAttribute("error_book", bookMessage);
            model.addAttribute("error_date", dateMessage);
            return "lib/new-loan";
        }

        loanService.saveLoan(loan);
        return "redirect:/lib-dashboard/loans/page/1/?phrase=&loan_create=success";
    }

    @DeleteMapping
    @RequestMapping("/delete-loan/{loanId}")
    public String deleteLoan(@PathVariable("loanId") Long id) {
        // check if loan exists in data base
        Loan loanToDelete = loanService.findLoanById(id);
        if(loanToDelete == null) {
            return "error-page";
        } else {
            loanService.deleteLoan(id);
            return "redirect:/lib-dashboard/loans/page/1/?phrase=&loan_delete=success";
        }
    }
}