package br.com.fiap.sprint4.investments_api.service;

import br.com.fiap.sprint4.investments_api.dto.InvestmentDTO;

import java.util.List;

public interface InvestmentService {
    InvestmentDTO createInvestment(InvestmentDTO dto, String username);
    List<InvestmentDTO> listInvestments(String username);
    InvestmentDTO updateInvestment(Long id, InvestmentDTO dto, String username);
    void deleteInvestment(Long id, String username);
}
