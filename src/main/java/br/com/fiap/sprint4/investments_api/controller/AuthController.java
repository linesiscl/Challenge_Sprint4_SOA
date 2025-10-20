package br.com.fiap.sprint4.investments_api.controller;

import br.com.fiap.sprint4.investments_api.dto.AuthRequestDTO;
import br.com.fiap.sprint4.investments_api.dto.AuthResponseDTO;
import br.com.fiap.sprint4.investments_api.dto.UserDTO;
import br.com.fiap.sprint4.investments_api.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar novo usuário")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(authService.register(userDTO));
    }

    @PostMapping("/login")
    @Operation(summary = "Login e geração de token JWT")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
