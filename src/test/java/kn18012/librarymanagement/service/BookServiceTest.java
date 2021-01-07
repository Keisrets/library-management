package kn18012.librarymanagement.service;

import kn18012.librarymanagement.domain.Author;
import kn18012.librarymanagement.domain.Book;
import kn18012.librarymanagement.domain.Genre;
import kn18012.librarymanagement.repository.BookRepository;
import kn18012.librarymanagement.repository.GenreRepository;
import kn18012.librarymanagement.repository.LoanRepository;
import kn18012.librarymanagement.service.implementation.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @InjectMocks
    BookServiceImpl bookService;

    @Mock
    BookRepository bookRepository;
    @Mock
    GenreRepository genreRepository;
    @Mock
    LoanRepository loanRepository;

    private Book b1;

    @BeforeEach
    void setUp() {
        b1 = new Book(1L, "Test book", new HashSet<>(), new HashSet<>(), new Genre(), "test description", 3);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findAll() {
        List<Book> books = new ArrayList<>();
        books.add(new Book());

        List<Book> result1 = bookService.findAll();
        assertEquals(0, result1.size());

        when(bookRepository.findAll()).thenReturn(books);
        List<Book> result2 = bookService.findAll();
        assertEquals(1, result2.size());
    }

    @Test
    void findAllGenres() {
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre());

        List<Genre> result1 = bookService.findAllGenres();
        assertEquals(0, result1.size());

        when(genreRepository.findAll()).thenReturn(genres);
        List<Genre> result2 = bookService.findAllGenres();
        verify(genreRepository, atLeastOnce()).findAll();
        assertEquals(1, result2.size());
    }

    @Test
    void findById() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(b1));
        when(bookRepository.findById(0L)).thenReturn(Optional.of(new Book(0L, "not found", new HashSet<>(), new HashSet<>(), new Genre(), "t", 1)));

        Book result1 = bookService.findById(1L);
        Book result2 = bookService.findById(0L);

        verify(bookRepository, atLeastOnce()).findById(anyLong());
        assertEquals(result1, b1);
        assertEquals("not found", result2.getTitle());
    }

    @Test
    void save() {
        when(bookRepository.save(any(Book.class))).thenReturn(b1);
        Book result = bookService.save(new Book());

        verify(bookRepository, atLeastOnce()).save(any());
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void update() {
        Book b2 = new Book(2L, "new title", new HashSet<>(), new HashSet<>(), new Genre(), "description", 1);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(b1));
        when(bookRepository.save(any(Book.class))).thenReturn(b1);

        Book result = bookService.update(1L, b2);

        verify(bookRepository, atLeastOnce()).findById(anyLong());
        verify(bookRepository, atLeastOnce()).save(any(Book.class));

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("new title", result.getTitle());

    }

    @Test
    void deleteBookById() {
        bookService.deleteBookById(1L);
        verify(bookRepository, atLeastOnce()).deleteById(anyLong());
    }
}