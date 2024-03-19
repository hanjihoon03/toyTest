package toypro.developer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toypro.developer.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
