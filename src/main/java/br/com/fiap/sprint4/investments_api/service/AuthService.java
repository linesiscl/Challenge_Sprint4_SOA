package br.com.fiap.sprint4.investments_api.service;

import br.com.fiap.sprint4.investments_api.dto.AuthRequestDTO;
import br.com.fiap.sprint4.investments_api.dto.AuthResponseDTO;
import br.com.fiap.sprint4.investments_api.dto.UserDTO;

public interface AuthService {
    AuthResponseDTO authenticate(AuthRequestDTO request);
    UserDTO register(UserDTO userDTO);
}
