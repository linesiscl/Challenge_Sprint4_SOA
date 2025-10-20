package br.com.fiap.sprint4.investments_api.controller;

import br.com.fiap.sprint4.investments_api.dto.InvestmentDTO;
import br.com.fiap.sprint4.investments_api.service.InvestmentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/investments")
public class InvestmentController {

    private final InvestmentService investmentService;
    public InvestmentController(InvestmentService investmentService) {
        this.investmentService = investmentService;
    }

    @GetMapping
    @Operation(summary = "Listar investimentos do usuário autenticado")
    public ResponseEntity<List<InvestmentDTO>> list(Authentication authentication) {
        return ResponseEntity.ok(investmentService.listInvestments(authentication.getName()));
    }

    @PostMapping
    @Operation(summary = "Criar novo investimento")
    public ResponseEntity<InvestmentDTO> create(@RequestBody InvestmentDTO dto, Authentication auth) {
        return ResponseEntity.ok(investmentService.createInvestment(dto, auth.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvestmentDTO> update(@PathVariable Long id, @RequestBody InvestmentDTO dto, Authentication auth) {
        return ResponseEntity.ok(investmentService.updateInvestment(id, dto, auth.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication auth) {
        investmentService.deleteInvestment(id, auth.getName());
        return ResponseEntity.noContent().build();
    }


}
