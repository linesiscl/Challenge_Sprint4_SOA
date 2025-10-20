package br.com.fiap.sprint4.investments_api.service;

import br.com.fiap.sprint4.investments_api.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);
}
