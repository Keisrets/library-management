package kn18012.librarymanagement.repository;

import kn18012.librarymanagement.domain.Loan;
import kn18012.librarymanagement.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findLoansByUser(User user);

    Page<Loan> findLoansByUser_FirstNameIgnoreCaseContainingOrUser_LastNameIgnoreCaseContaining(String firstName, String lastName, Pageable pageable);
}
