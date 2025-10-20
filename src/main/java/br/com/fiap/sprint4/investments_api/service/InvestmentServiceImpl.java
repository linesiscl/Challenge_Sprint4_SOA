package br.com.fiap.sprint4.investments_api.service;

import br.com.fiap.sprint4.investments_api.dto.InvestmentDTO;
import br.com.fiap.sprint4.investments_api.entity.Investment;
import br.com.fiap.sprint4.investments_api.entity.User;
import br.com.fiap.sprint4.investments_api.repository.InvestmentRepository;
import br.com.fiap.sprint4.investments_api.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvestmentServiceImpl implements InvestmentService {

    private final InvestmentRepository investmentRepository;
    private final UserRepository userRepository;

    public InvestmentServiceImpl(InvestmentRepository investmentRepository, UserRepository userRepository) {
        this.investmentRepository = investmentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public InvestmentDTO createInvestment(InvestmentDTO dto, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Investment investment = new Investment();
        investment.setAssetName(dto.getAssetName());
        investment.setAmount(dto.getAmount());
        investment.setOwner(user);
        investmentRepository.save(investment);
        dto.setId(investment.getId());
        return dto;
    }

    @Override
    public List<InvestmentDTO> listInvestments(String username) {
        return investmentRepository.findByOwnerUsername(username)
                .stream()
                .map(inv -> {
                    InvestmentDTO dto = new InvestmentDTO();
                    dto.setId(inv.getId());
                    dto.setAssetName(inv.getAssetName());
                    dto.setAmount(inv.getAmount());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public InvestmentDTO updateInvestment(Long id, InvestmentDTO dto, String username) {
        Investment investment = investmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Investimento não encontrado"));

        if (!investment.getOwner().getUsername().equals(username)) {
            throw new RuntimeException("Não autorizado");
        }

        investment.setAssetName(dto.getAssetName());
        investment.setAmount(dto.getAmount());
        investmentRepository.save(investment);

        dto.setId(investment.getId());
        return dto;
    }

    @Override
    public void deleteInvestment(Long id, String username) {
        Investment investment = investmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Investimento não encontrado"));

        if (!investment.getOwner().getUsername().equals(username)) {
            throw new RuntimeException("Não autorizado");
        }

        investmentRepository.delete(investment);
    }
}
