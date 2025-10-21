package br.com.fiap.sprint4.investments_api.config;

import br.com.fiap.sprint4.investments_api.security.JwtUserDetailsService;
import br.com.fiap.sprint4.investments_api.security.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private JwtUserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldSkipAuthEndpoints() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/auth/login");

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        verifyNoInteractions(jwtUtil);
    }

    @Test
    void shouldAuthenticateWithValidToken() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/investments");
        when(request.getHeader("Authorization")).thenReturn("Bearer validtoken");

        UserDetails userDetails = User.withUsername("aline").password("123").roles("USER").build();

        when(jwtUtil.extractUsername("validtoken")).thenReturn("aline");
        when(userDetailsService.loadUserByUsername("aline")).thenReturn(userDetails);
    }
}