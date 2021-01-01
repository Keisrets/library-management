package kn18012.librarymanagement.service.implementation;

import kn18012.librarymanagement.domain.Author;
import kn18012.librarymanagement.domain.Book;
import kn18012.librarymanagement.repository.AuthorRepository;
import kn18012.librarymanagement.repository.BookRepository;
import kn18012.librarymanagement.service.AuthorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        return authorRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Author> searchForAuthor(String phrase, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 30);
        return authorRepository.findByFirstNameIgnoreCaseContainingOrLastNameIgnoreCaseContaining(phrase, phrase, pageable);
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

        // delete author from associated book object author sets
        if(bookIds.size() > 0) {
            for (Book book : bookIds) {
                Book toUpdate = bookRepository.findById(book.getId()).orElse(null);
                if(toUpdate != null) {
                    toUpdate.getAuthors().remove(toDelete);
                }
                toUpdate = null;
            }

            toDelete.getBooks().clear();
        }

        authorRepository.delete(toDelete);
    }
}
