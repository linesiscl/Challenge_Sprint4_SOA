package br.com.fiap.sprint4.investments_api.security;

import br.com.fiap.sprint4.investments_api.entity.Role;
import br.com.fiap.sprint4.investments_api.entity.User;
import br.com.fiap.sprint4.investments_api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private JwtUserDetailsService jwtUserDetailsService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldLoadUserByUsernameSuccessfully() {
        // given
        User user = new User();
        user.setUsername("aline");
        user.setPassword("123");
        Role role = new Role();
        role.setName("ROLE_USER");
        user.setRoles(Set.of(role));

        when(userRepository.findByUsername("aline")).thenReturn(Optional.of(user));

        // when
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername("aline");

        // then
        assertNotNull(userDetails);
        assertEquals("aline", userDetails.getUsername());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
        verify(userRepository, times(1)).findByUsername("aline");
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findByUsername("notfound")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () ->
                jwtUserDetailsService.loadUserByUsername("notfound"));
    }
}

