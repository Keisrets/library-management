package kn18012.librarymanagement.service;

import kn18012.librarymanagement.domain.Book;
import kn18012.librarymanagement.domain.Loan;
import kn18012.librarymanagement.domain.User;

public interface LibrarianService {

    Loan saveLoan(Loan loan);

}
