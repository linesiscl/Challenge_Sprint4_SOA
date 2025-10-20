package br.com.fiap.sprint4.investments_api.service;

import br.com.fiap.sprint4.investments_api.entity.User;
import br.com.fiap.sprint4.investments_api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnUserWhenUsernameExists() {
        User user = new User();
        user.setUsername("aline");
        when(userRepository.findByUsername("aline")).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByUsername("aline");

        assertTrue(result.isPresent());
        assertEquals("aline", result.get().getUsername());
        verify(userRepository, times(1)).findByUsername("aline");
    }

    @Test
    void shouldReturnEmptyWhenUsernameDoesNotExist() {
        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        Optional<User> result = userService.findByUsername("nonexistent");

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findByUsername("nonexistent");
    }
}

