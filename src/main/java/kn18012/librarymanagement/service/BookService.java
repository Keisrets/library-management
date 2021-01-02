package kn18012.librarymanagement.service;

import kn18012.librarymanagement.domain.Book;
import kn18012.librarymanagement.domain.Genre;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookService {

    List<Book> findAll();

    List<Genre> findAllGenres();

    Page<Book> searchForBook(String phrase, int pageNumber);

    Book findById(Long id);

    Book save(Book book);

    Book update(Long id, Book book);

    void deleteBookById(Long id);
}
