package kn18012.librarymanagement.repository;

import kn18012.librarymanagement.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}
