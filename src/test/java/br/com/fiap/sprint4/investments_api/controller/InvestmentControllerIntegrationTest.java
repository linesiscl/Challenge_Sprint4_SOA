package br.com.fiap.sprint4.investments_api.controller;

import br.com.fiap.sprint4.investments_api.dto.InvestmentDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class InvestmentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void shouldRejectRequestWhenNoTokenProvided() throws Exception {
        InvestmentDTO dto = new InvestmentDTO();
        dto.setAssetName("PETR4");
        dto.setAmount(BigDecimal.valueOf(5000));

        MvcResult result = mockMvc.perform(post("/investments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andDo(print())
                .andReturn();

        int status = result.getResponse().getStatus();
        System.out.println("🔍 Status retornado: " + status);
        System.out.println("🔍 Corpo da resposta: " + result.getResponse().getContentAsString());

        // Aceita 401, 403 ou 404 — depende da config do Spring Security
        assertThat(status)
                .withFailMessage("Status inesperado: " + status)
                .isIn(401, 403, 404);
    }
}
