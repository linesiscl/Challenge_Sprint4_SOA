package br.com.fiap.sprint4.investments_api.repository;

import br.com.fiap.sprint4.investments_api.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
