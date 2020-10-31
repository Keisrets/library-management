package kn18012.librarymanagement.service.implementation;

import kn18012.librarymanagement.domain.Author;
import kn18012.librarymanagement.domain.Book;
import kn18012.librarymanagement.repository.AuthorRepository;
import kn18012.librarymanagement.repository.BookRepository;
import kn18012.librarymanagement.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;
    private BookRepository bookRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Author findAuthorById(Long id) {
        // handle null return
        return authorRepository.findById(id).orElse(null);
    }

    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
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
