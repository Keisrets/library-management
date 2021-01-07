package kn18012.librarymanagement.service;

import kn18012.librarymanagement.domain.*;
import kn18012.librarymanagement.repository.BookRepository;
import kn18012.librarymanagement.repository.LoanRepository;
import kn18012.librarymanagement.service.implementation.LoanServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoanServiceTest {

    @InjectMocks
    LoanServiceImpl loanService;

    @Mock
    LoanRepository loanRepository;
    @Mock
    BookRepository bookRepository;
    @Mock
    BookService bookService;

    private Loan l1;
    private User u1;
    private Book b1;

    @BeforeEach
    void setUp() {
        u1 = new User(1L, "Kristers", "Nikitins", "kn@test.lv", "pass123", new ArrayList<>(), new HashSet<>());
        b1 = new Book(1L, "Test book", new HashSet<>(), new HashSet<>(), new Genre(), "test description", 3);
        l1 = new Loan(1L, u1, b1, LocalDate.now(), LocalDate.now().plusWeeks(2));

        Set<Loan> userLoans = new HashSet<>();
        userLoans.add(l1);
        u1.setLoans(userLoans);

        Set<Loan> bookLoans = new HashSet<>();
        bookLoans.add(l1);
        b1.setLoans(bookLoans);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findLoansByUser() {
        List<Loan> loans = new ArrayList<>();
        loans.add(new Loan());

        List<Loan> result1 = loanService.findLoansByUser(u1);

        when(loanRepository.findLoansByUser(u1)).thenReturn(loans);
        List<Loan> result2 = loanService.findLoansByUser(u1);

        assertEquals(0, result1.size());
        assertEquals(1, result2.size());
        verify(loanRepository, times(2)).findLoansByUser(u1);
    }

    @Test
    void saveLoan() {
        when(loanRepository.save(l1)).thenReturn(l1);
        when(bookRepository.findById(b1.getId())).thenReturn(Optional.of(b1));
        Loan result = loanService.saveLoan(l1);
        verify(bookRepository, atLeastOnce()).findById(b1.getId());
        assertEquals(1L, result.getId());
        assertEquals(2, result.getBook().getQuantity());
    }

    // test not working
    @Test
    void deleteLoan() {
        when(loanRepository.findById(l1.getId())).thenReturn(Optional.of(l1));
        loanService.deleteLoan(l1.getId());
        verify(loanRepository, times(1)).deleteById(l1.getId());
    }
}