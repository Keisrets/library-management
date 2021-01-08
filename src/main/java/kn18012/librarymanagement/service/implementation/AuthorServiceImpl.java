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
        // set how many results are displayed per page
        Pageable pageable = PageRequest.of(pageNumber - 1, 30);
        // return author search results
        return authorRepository.findByFirstNameIgnoreCaseContainingOrLastNameIgnoreCaseContaining(phrase, phrase, pageable);
    }

    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Author update(Long id, Author author) {
        Author authorToUpdate = authorRepository.findById(id).orElse(null);
        // update author fields
        authorToUpdate.setFirstName(author.getFirstName());
        authorToUpdate.setLastName(author.getLastName());
        authorToUpdate.setBooks(author.getBooks());

        return authorRepository.save(authorToUpdate);
    }

    @Override
    public void deleteAuthorById(Long id) {
        Author authorToDelete = authorRepository.findById(id).orElse(null);

        if(authorToDelete != null) {
            Set<Book> bookIds = authorToDelete.getBooks();

            // firstly delete author from associated book object author sets
            if(bookIds.size() > 0) {
                for (Book book : bookIds) {
                    Book toUpdate = bookRepository.findById(book.getId()).orElse(null);
                    if(toUpdate != null) {
                        toUpdate.getAuthors().remove(authorToDelete);
                    }
                    toUpdate = null;
                }
                // delete books from author book set
                authorToDelete.getBooks().clear();
            }

            authorRepository.delete(authorToDelete);
        }
    }
}