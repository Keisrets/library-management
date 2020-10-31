package kn18012.librarymanagement.service.implementation;

import kn18012.librarymanagement.domain.Book;
import kn18012.librarymanagement.repository.BookRepository;
import kn18012.librarymanagement.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
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
