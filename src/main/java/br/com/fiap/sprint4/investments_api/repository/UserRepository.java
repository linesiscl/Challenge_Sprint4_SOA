package br.com.fiap.sprint4.investments_api.repository;

import br.com.fiap.sprint4.investments_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
