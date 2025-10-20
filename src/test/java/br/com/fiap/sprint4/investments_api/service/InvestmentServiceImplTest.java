package br.com.fiap.sprint4.investments_api.service;

import br.com.fiap.sprint4.investments_api.dto.InvestmentDTO;
import br.com.fiap.sprint4.investments_api.entity.Investment;
import br.com.fiap.sprint4.investments_api.entity.User;
import br.com.fiap.sprint4.investments_api.repository.InvestmentRepository;
import br.com.fiap.sprint4.investments_api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InvestmentServiceImplTest {

    @Mock
    private InvestmentRepository investmentRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private InvestmentServiceImpl investmentService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateInvestmentSuccessfully() {
        User user = new User();
        user.setUsername("aline");

        InvestmentDTO dto = new InvestmentDTO();
        dto.setAssetName("PETR4");
        dto.setAmount(BigDecimal.valueOf(1000));

        when(userRepository.findByUsername("aline")).thenReturn(Optional.of(user));
        when(investmentRepository.save(any(Investment.class))).thenAnswer(i -> i.getArgument(0));

        InvestmentDTO result = investmentService.createInvestment(dto, "aline");

        assertEquals("PETR4", result.getAssetName());
        verify(investmentRepository, times(1)).save(any(Investment.class));
    }

    @Test
    void shouldListInvestmentsByUser() {
        User user = new User();
        user.setUsername("aline");

        Investment inv = new Investment();
        inv.setId(1L);
        inv.setAssetName("AAPL");
        inv.setAmount(BigDecimal.valueOf(2000));
        inv.setOwner(user);

        when(investmentRepository.findByOwnerUsername("aline")).thenReturn(List.of(inv));

        List<InvestmentDTO> list = investmentService.listInvestments("aline");

        assertEquals(1, list.size());
        assertEquals("AAPL", list.get(0).getAssetName());
    }
}
