package com.pruebaTecnica.msvc.cuentamovimientos.IntegrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pruebaTecnica.msvc.cuentamovimientos.controllers.CuentaController;
import com.pruebaTecnica.msvc.cuentamovimientos.models.entitys.Cuenta;
import com.pruebaTecnica.msvc.cuentamovimientos.models.entitys.Movimiento;
import com.pruebaTecnica.msvc.cuentamovimientos.services.CuentaService;
import com.pruebaTecnica.msvc.cuentamovimientos.services.MovientoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
public class IntegrationTest {
    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private MovientoService movimientoService;

    private final MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    public IntegrationTest() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(CuentaController.class).build();
    }

    @Test
    public void testIntegration() throws Exception {
        // Crear una cuenta
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(123456789L);
        cuenta.setTipoCuenta("Ahorros");
        cuenta.setSaldoInicial(1000L);
        cuenta.setEstado("Activa");

        cuenta = cuentaService.guardar(cuenta);

        // Realizar una transacción
        Movimiento movimiento = new Movimiento();
        movimiento.setTipoMoviento("Depósito");
        movimiento.setValor(500L);
        movimiento.setCuentaId(cuenta);

        movimiento = movimientoService.guardar(movimiento);

        // Verificar que la transacción se registró correctamente
        mockMvc.perform(MockMvcRequestBuilders.get("/cuentas/" + cuenta.getCuentaId() + "/movimientos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].valor").value(500));
    }
}
