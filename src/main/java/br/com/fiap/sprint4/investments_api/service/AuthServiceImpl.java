package br.com.fiap.sprint4.investments_api.service;

import br.com.fiap.sprint4.investments_api.dto.AuthRequestDTO;
import br.com.fiap.sprint4.investments_api.dto.AuthResponseDTO;
import br.com.fiap.sprint4.investments_api.dto.UserDTO;
import br.com.fiap.sprint4.investments_api.entity.Role;
import br.com.fiap.sprint4.investments_api.entity.User;
import br.com.fiap.sprint4.investments_api.repository.RoleRepository;
import br.com.fiap.sprint4.investments_api.repository.UserRepository;
import br.com.fiap.sprint4.investments_api.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authManager;
    private final RoleRepository roleRepository;

    public AuthServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder encoder,
                           JwtUtil jwtUtil,
                           AuthenticationManager authManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
        this.authManager = authManager;
    }

    @Override
    public AuthResponseDTO authenticate(AuthRequestDTO request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        var userDetails = new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toSet())
        );

        String token = jwtUtil.generateToken(userDetails);

        return new AuthResponseDTO(token);
    }

    public UserDTO register(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(encoder.encode(userDTO.getPassword()));

        Role defaultRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName("ROLE_USER");
                    return roleRepository.save(role);
                });

        user.setRoles(Set.of(defaultRole));
        userRepository.save(user);

        return userDTO;
    }
}
