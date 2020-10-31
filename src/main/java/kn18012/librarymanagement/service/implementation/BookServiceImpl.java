package kn18012.librarymanagement.service.implementation;

import kn18012.librarymanagement.domain.Book;
import kn18012.librarymanagement.repository.AuthorRepository;
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
}
