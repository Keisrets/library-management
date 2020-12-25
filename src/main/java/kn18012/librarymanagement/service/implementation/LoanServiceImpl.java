package kn18012.librarymanagement.service.implementation;

import kn18012.librarymanagement.domain.Book;
import kn18012.librarymanagement.domain.Loan;
import kn18012.librarymanagement.domain.User;
import kn18012.librarymanagement.repository.BookRepository;
import kn18012.librarymanagement.repository.LoanRepository;
import kn18012.librarymanagement.service.BookService;
import kn18012.librarymanagement.service.LoanService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {

    private LoanRepository loanRepository;
    private BookRepository bookRepository;
    private BookService bookService;

    public LoanServiceImpl(LoanRepository loanRepository, BookRepository bookRepository, BookService bookService) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.bookService = bookService;
    }

    @Override
    public List<Loan> findAllLoans() {
        return loanRepository.findAll();
    }

    @Override
    public List<Loan> findLoansByUser(User user) {
        return loanRepository.findLoansByUser(user);
    }

    @Override
    public Page<Loan> searchForLoan(String phrase, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 8);
        return loanRepository.findLoansByUser_FirstNameIgnoreCaseContainingOrUser_LastNameIgnoreCaseContaining(phrase, phrase, pageable);
    }

    @Override
    public Loan saveLoan(Loan loan) {
        Book updatedBook = bookRepository.findById(loan.getBook().getId()).orElse(null);
        if(updatedBook != null) {
            if(updatedBook.getQuantity() > 0) {
                updatedBook.setQuantity(updatedBook.getQuantity() - 1);
                bookService.update(loan.getBook().getId(), updatedBook);
                loan.setStart_date(LocalDate.now());
                return loanRepository.save(loan);
            }
        }

        return null;
    }

    @Override
    public Loan updateLoan(Long id) {
        Loan updatedLoan = loanRepository.findById(id).orElse(null);
        LocalDate date = updatedLoan.getEnd_date();

        LocalDate newEndDate = date.plusMonths(1);
        updatedLoan.setEnd_date(newEndDate);

        return loanRepository.save(updatedLoan);
    }

    @Override
    public void deleteLoan(Long id) {
        Book loanedBook = loanRepository.findById(id).get().getBook();
        loanedBook.setQuantity(loanedBook.getQuantity() + 1);
        bookService.update(loanedBook.getId(), loanedBook);
        loanRepository.deleteById(id);
    }
}
