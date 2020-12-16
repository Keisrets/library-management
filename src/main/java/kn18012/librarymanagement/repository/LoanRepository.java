package kn18012.librarymanagement.repository;

import kn18012.librarymanagement.domain.Loan;
import kn18012.librarymanagement.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findLoansByUser(User user);
}
