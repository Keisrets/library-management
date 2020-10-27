package kn18012.librarymanagement.service;


import kn18012.librarymanagement.domain.Author;
import kn18012.librarymanagement.domain.Book;

public interface LibrarianService {

    Book save(Book book);

    Author save(Author author);

    Book update(Long id, Book book);

    Author update(Author author);

    void delete(Book book);

    void delete(Author author);

    void deleteBookById(Long id);

    void deleteAuthorById(Long id);

}
