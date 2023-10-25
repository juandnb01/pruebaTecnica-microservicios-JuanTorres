package com.pruebaTecnica.msvc.clientepersona.controllers;

import com.pruebaTecnica.msvc.clientepersona.entitys.ClienteController;
import com.pruebaTecnica.msvc.clientepersona.models.entitys.Cliente;
import com.pruebaTecnica.msvc.clientepersona.services.ClienteService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

;import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Test
    public void testDetalleClienteExistente() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setId(1L);

        Mockito.when(clienteService.porId(1L)).thenReturn(Optional.of(cliente));

        mockMvc.perform(get("/api/clientes/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDetalleClienteNoExistente() throws Exception {
        Mockito.when(clienteService.porId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/clientes/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testListarClientes() throws Exception {
        Cliente cliente1 = new Cliente();
        cliente1.setId(1L);
        cliente1.setNombre("Cliente 1");

        Cliente cliente2 = new Cliente();
        cliente2.setId(2L);
        cliente2.setNombre("Cliente 2");

        List<Cliente> clientes = Arrays.asList(cliente1, cliente2);

        Mockito.when(clienteService.listar()).thenReturn(clientes);

        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Cliente 1"))
                .andExpect(jsonPath("$[1].nombre").value("Cliente 2"));
    }

}