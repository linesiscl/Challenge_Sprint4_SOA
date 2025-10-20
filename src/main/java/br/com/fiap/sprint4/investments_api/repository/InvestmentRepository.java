package br.com.fiap.sprint4.investments_api.repository;

import br.com.fiap.sprint4.investments_api.entity.Investment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvestmentRepository extends JpaRepository<Investment, Long> {
    List<Investment> findByOwnerUsername(String username);
}
