package br.com.fiap.sprint4.investments_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenApiConfigTest {

    @Test
    void shouldCreateOpenAPIBeanCorrectly() {
        OpenApiConfig config = new OpenApiConfig();
        OpenAPI api = config.investmentAPI();

        Info info = api.getInfo();
        assertNotNull(info);
        assertEquals("Investment API", info.getTitle());
        assertEquals("1.0", info.getVersion());

        assertTrue(api.getComponents().getSecuritySchemes().containsKey("BearerAuth"));
        SecurityScheme scheme = api.getComponents().getSecuritySchemes().get("BearerAuth");
        assertEquals("bearer", scheme.getScheme());
    }
}

