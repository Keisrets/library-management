package kn18012.librarymanagement.service;

import kn18012.librarymanagement.domain.Author;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AuthorService {

    List<Author> findAllAuthors();

    Author findAuthorById(Long id);

    Page<Author> searchForAuthor(String phrase, int pageNumber);

    Author save(Author author);

    Author update(Long id, Author author);

    void deleteAuthorById(Long id);
}
