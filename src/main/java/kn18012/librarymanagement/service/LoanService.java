package kn18012.librarymanagement.service;

import kn18012.librarymanagement.domain.Loan;
import kn18012.librarymanagement.domain.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LoanService {

    List<Loan> findLoansByUser(User user);

    Page<Loan> searchForLoan(String phrase, int pageNumber);

    Loan saveLoan(Loan loan);

    void deleteLoan(Long id);
}
