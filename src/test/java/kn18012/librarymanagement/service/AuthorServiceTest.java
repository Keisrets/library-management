package kn18012.librarymanagement.service;

import kn18012.librarymanagement.domain.Author;
import kn18012.librarymanagement.repository.AuthorRepository;
import kn18012.librarymanagement.service.implementation.AuthorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorServiceTest {

    @InjectMocks
    AuthorServiceImpl authorService;

    @Mock
    AuthorRepository authorRepository;

    private Author a1;

    @BeforeEach
    void setUp() {
        a1 = new Author(1L, "Kristers", "Nikitins", new HashSet<>());

        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findAllAuthors() {
        Author a2 = new Author(2L, "Janis", "Berzs", new HashSet<>());
        List<Author> authors = new ArrayList<>();
        authors.add(a1);
        authors.add(a2);

        List<Author> result1 = authorService.findAllAuthors();
        assertEquals(0, result1.size());

        when(authorRepository.findAll()).thenReturn(authors);
        List<Author> result = authorService.findAllAuthors();

        verify(authorRepository, atLeastOnce()).findAll();
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void findAuthorById() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(a1));
        when(authorRepository.findById(0L)).thenReturn(Optional.of(new Author(0L, "not-found", "t", new HashSet<>())));

        Author result1 = authorService.findAuthorById(1L);
        Author result2 = authorService.findAuthorById(0L);

        verify(authorRepository, atLeastOnce()).findById(anyLong());
        assertEquals(result1, a1);
        assertEquals("not-found", result2.getFirstName());
    }

    @Test
    void save() {
        when(authorRepository.save(any(Author.class))).thenReturn(a1);
        Author result = authorService.save(new Author());

        verify(authorRepository, atLeastOnce()).save(any());
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void update() {
        Author a2 = new Author(2L, "Janis", "Berzs", new HashSet<>());

        when(authorRepository.findById(1L)).thenReturn(Optional.of(a1));
        when(authorRepository.save(any(Author.class))).thenReturn(a1);

        Author result = authorService.update(1L, a2);

        verify(authorRepository, atLeastOnce()).findById(anyLong());
        verify(authorRepository, atLeastOnce()).save(any(Author.class));

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Janis", result.getFirstName());
        assertEquals("Berzs", result.getLastName());
    }

    @Test
    void deleteAuthorById() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(a1));
        authorService.deleteAuthorById(1L);
        verify(authorRepository, atLeastOnce()).delete(any(Author.class));
    }
}