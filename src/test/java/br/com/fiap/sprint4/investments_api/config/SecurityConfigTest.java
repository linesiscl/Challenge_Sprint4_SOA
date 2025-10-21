package br.com.fiap.sprint4.investments_api.config;

import br.com.fiap.sprint4.investments_api.security.JwtUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

class SecurityConfigTest {

    @Mock
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private JwtUserDetailsService userDetailsService;

    private SecurityConfig securityConfig;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        securityConfig = new SecurityConfig(jwtAuthenticationFilter, userDetailsService);
    }

    @Test
    void shouldCreatePasswordEncoderBean() {
        PasswordEncoder encoder = securityConfig.passwordEncoder();
        assertNotNull(encoder);
        assertTrue(encoder.matches("123", encoder.encode("123")));
    }

    @Test
    void shouldCreateSecurityFilterChain() throws Exception {
        HttpSecurity http = mock(HttpSecurity.class, RETURNS_DEEP_STUBS);
        SecurityFilterChain chain = securityConfig.securityFilterChain(http);
        assertNotNull(chain);
    }
}
