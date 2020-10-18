package kn18012.librarymanagement.repository;

import kn18012.librarymanagement.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
