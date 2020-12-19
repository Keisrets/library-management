package kn18012.librarymanagement.service;

import kn18012.librarymanagement.domain.Book;
import kn18012.librarymanagement.domain.Genre;

import java.util.List;

public interface BookService {

    List<Book> findAll();

    List<Genre> findAllGenres();

    Book findById(Long id);

    Book save(Book book);

    Book update(Long id, Book book);

    void deleteBookById(Long id);

}
