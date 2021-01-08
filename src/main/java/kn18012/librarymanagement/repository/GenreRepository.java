package kn18012.librarymanagement.repository;

import kn18012.librarymanagement.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}