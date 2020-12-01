package kn18012.librarymanagement.service.implementation;

import kn18012.librarymanagement.domain.Book;
import kn18012.librarymanagement.domain.Loan;
import kn18012.librarymanagement.repository.BookRepository;
import kn18012.librarymanagement.repository.LoanRepository;
import kn18012.librarymanagement.service.BookService;
import kn18012.librarymanagement.service.LibrarianService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LibrarianServiceImpl implements LibrarianService {

    private LoanRepository loanRepository;
    private BookRepository bookRepository;
    private BookService bookService;

    public LibrarianServiceImpl(LoanRepository loanRepository, BookRepository bookRepository, BookService bookService) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.bookService = bookService;
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
    public List<Loan> findAllLoans() {
        return loanRepository.findAll();
    }

    @Override
    public void deleteLoan(Long id) {
        loanRepository.deleteById(id);
    }

    @Override
    public Loan updateLoan(Long id) {
        Loan updatedLoan = loanRepository.findById(id).orElse(null);
        LocalDate date = updatedLoan.getEnd_date();

        LocalDate newEndDate = date.plusMonths(1);
        updatedLoan.setEnd_date(newEndDate);

        return loanRepository.save(updatedLoan);
    }
}
