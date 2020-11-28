package kn18012.librarymanagement.service;

import kn18012.librarymanagement.domain.Loan;
import java.util.List;

public interface LibrarianService {

    Loan saveLoan(Loan loan);

    List<Loan> findAllLoans();

    void deleteLoan(Long id);

}
