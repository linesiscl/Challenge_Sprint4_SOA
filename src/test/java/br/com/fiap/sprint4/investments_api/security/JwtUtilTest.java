package br.com.fiap.sprint4.investments_api.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private UserDetails userDetails;

    @BeforeEach
    void setup() {
        jwtUtil = new JwtUtil();
        // Configura manualmente o secret e o tempo de expiração
        jwtUtil.getClass().getDeclaredFields();
        jwtUtil = new JwtUtil();
        try {
            var secretField = JwtUtil.class.getDeclaredField("secret");
            secretField.setAccessible(true);
            secretField.set(jwtUtil, "12345678901234567890123456789012"); // 32 chars

            var expirationField = JwtUtil.class.getDeclaredField("expirationMs");
            expirationField.setAccessible(true);
            expirationField.set(jwtUtil, 3600000L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        userDetails = User.withUsername("aline")
                .password("123")
                .roles("USER")
                .build();
    }

    @Test
    void shouldGenerateValidToken() {
        String token = jwtUtil.generateToken(userDetails);
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void shouldExtractUsernameFromToken() {
        String token = jwtUtil.generateToken(userDetails);
        String username = jwtUtil.extractUsername(token);
        assertEquals("aline", username);
    }

    @Test
    void shouldValidateTokenSuccessfully() {
        String token = jwtUtil.generateToken(userDetails);
        boolean isValid = jwtUtil.validateToken(token, userDetails);
        assertTrue(isValid);
    }

    @Test
    void shouldInvalidateTokenWithDifferentUser() {
        String token = jwtUtil.generateToken(userDetails);
        UserDetails otherUser = User.withUsername("maria")
                .password("456")
                .roles("USER")
                .build();
        boolean isValid = jwtUtil.validateToken(token, otherUser);
        assertFalse(isValid);
    }
}

