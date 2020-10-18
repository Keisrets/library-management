package kn18012.librarymanagement.repository;

import kn18012.librarymanagement.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
