package kn18012.librarymanagement.service;

import kn18012.librarymanagement.domain.Author;

import java.util.List;

public interface AuthorService {

    List<Author> findAllAuthors();

    Author findAuthorById(Long id);
}
