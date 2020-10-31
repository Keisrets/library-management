package kn18012.librarymanagement.service.implementation;

import kn18012.librarymanagement.domain.Author;
import kn18012.librarymanagement.repository.AuthorRepository;
import kn18012.librarymanagement.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
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
}
