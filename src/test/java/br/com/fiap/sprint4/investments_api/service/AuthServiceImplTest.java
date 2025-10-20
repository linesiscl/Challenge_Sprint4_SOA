package br.com.fiap.sprint4.investments_api.service;

import br.com.fiap.sprint4.investments_api.dto.AuthRequestDTO;
import br.com.fiap.sprint4.investments_api.dto.AuthResponseDTO;
import br.com.fiap.sprint4.investments_api.dto.UserDTO;
import br.com.fiap.sprint4.investments_api.entity.Role;
import br.com.fiap.sprint4.investments_api.entity.User;
import br.com.fiap.sprint4.investments_api.repository.RoleRepository;
import br.com.fiap.sprint4.investments_api.repository.UserRepository;
import br.com.fiap.sprint4.investments_api.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AuthenticationManager authManager;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldAuthenticateSuccessfully() {
        AuthRequestDTO request = new AuthRequestDTO();
        request.setUsername("aline");
        request.setPassword("123");

        User user = new User();
        user.setUsername("aline");
        user.setPassword("encoded");

        when(userRepository.findByUsername("aline")).thenReturn(Optional.of(user));
        when(jwtUtil.generateToken(any(UserDetails.class))).thenReturn("fake-jwt-token");

        AuthResponseDTO response = authService.authenticate(request);

        assertNotNull(response);
        assertEquals("fake-jwt-token", response.getToken());
        verify(authManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void shouldRegisterNewUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("aline");
        userDTO.setPassword("123");

        when(passwordEncoder.encode("123")).thenReturn("encoded-pass");

        Role role = new Role();
        role.setName("ROLE_USER");
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(role));
        when(roleRepository.save(any(Role.class))).thenReturn(role);

        User savedUser = new User();
        savedUser.setUsername("aline");
        savedUser.setPassword("encoded-pass");
        savedUser.setRoles(Set.of(role));
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserDTO saved = authService.register(userDTO);

        assertEquals("aline", saved.getUsername());
        verify(userRepository, times(1)).save(any(User.class));
    }

}
