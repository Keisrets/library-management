package kn18012.librarymanagement.service.implementation;

import kn18012.librarymanagement.domain.Book;
import kn18012.librarymanagement.domain.Genre;
import kn18012.librarymanagement.repository.BookRepository;
import kn18012.librarymanagement.repository.GenreRepository;
import kn18012.librarymanagement.repository.LoanRepository;
import kn18012.librarymanagement.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    BookRepository bookRepository;
    GenreRepository genreRepository;
    LoanRepository loanRepository;

    public BookServiceImpl(BookRepository bookRepository, GenreRepository genreRepository, LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.loanRepository = loanRepository;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public List<Genre> findAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public Page<Book> searchForBook(String phrase, int pageNumber) {
        // set how many results are displayed per page
        Pageable pageable = PageRequest.of(pageNumber - 1, 30, Sort.by("id"));
        // return book search results
        return bookRepository.findByTitleContainingIgnoreCase(phrase, pageable);
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book update(Long id, Book book) {
        Book b = bookRepository.findById(id).orElse(null);
        // update book fields
        b.setTitle(book.getTitle());
        b.setAuthors(book.getAuthors());
        b.setLoans(book.getLoans());
        b.setGenre(book.getGenre());
        b.setDescription(book.getDescription());
        b.setQuantity(book.getQuantity());

        return bookRepository.save(b);
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }
}
