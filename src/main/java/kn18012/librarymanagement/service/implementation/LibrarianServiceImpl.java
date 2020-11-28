package kn18012.librarymanagement.service.implementation;

import kn18012.librarymanagement.domain.Loan;
import kn18012.librarymanagement.repository.LoanRepository;
import kn18012.librarymanagement.service.LibrarianService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class LibrarianServiceImpl implements LibrarianService {

    private LoanRepository loanRepository;

    public LibrarianServiceImpl(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Override
    public Loan saveLoan(Loan loan) {
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        // LocalDate startDate = LocalDate.now();
        // String start_date = LocalDate.now().toString();

        loan.setStart_date(LocalDate.now());
        return loanRepository.save(loan);
    }

    @Override
    public List<Loan> findAllLoans() {
        return loanRepository.findAll();
    }
}
