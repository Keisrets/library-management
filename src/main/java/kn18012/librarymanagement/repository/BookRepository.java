package kn18012.librarymanagement.repository;

import kn18012.librarymanagement.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
