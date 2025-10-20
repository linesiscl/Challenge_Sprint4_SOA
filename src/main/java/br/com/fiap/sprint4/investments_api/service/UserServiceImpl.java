package br.com.fiap.sprint4.investments_api.service;

import br.com.fiap.sprint4.investments_api.repository.UserRepository;
import br.com.fiap.sprint4.investments_api.entity.User;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
