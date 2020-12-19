package kn18012.librarymanagement.service;

import kn18012.librarymanagement.domain.Loan;
import kn18012.librarymanagement.domain.User;

import java.util.List;

public interface LoanService {

    List<Loan> findLoansByUser(User user);

    List<Loan> findAllLoans();

    Loan saveLoan(Loan loan);

    Loan updateLoan(Long id);

    void deleteLoan(Long id);

}
