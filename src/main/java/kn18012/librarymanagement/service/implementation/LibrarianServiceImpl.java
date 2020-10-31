package kn18012.librarymanagement.service.implementation;

import kn18012.librarymanagement.domain.Author;
import kn18012.librarymanagement.domain.Book;
import kn18012.librarymanagement.repository.AuthorRepository;
import kn18012.librarymanagement.repository.BookRepository;
import kn18012.librarymanagement.service.LibrarianService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LibrarianServiceImpl implements LibrarianService {

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public LibrarianServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
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
    public Author update(Long id, Author author) {
        Author toUpdate = authorRepository.findById(id).orElse(null);
        toUpdate.setFirstName(author.getFirstName());
        toUpdate.setLastName(author.getLastName());
        toUpdate.setBooks(author.getBooks());

        return authorRepository.save(toUpdate);
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public void deleteAuthorById(Long id) {
        Author toDelete = authorRepository.findById(id).orElse(null);
        Set<Book> bookIds = toDelete.getBooks();

        for (Book book : bookIds) {
            Book toUpdate = bookRepository.findById(book.getId()).orElse(null);
            if(toUpdate != null) {
                toUpdate.getAuthors().remove(toDelete);
            }
            toUpdate = null;
        }

        toDelete.getBooks().clear();
        authorRepository.delete(toDelete);
    }
}
