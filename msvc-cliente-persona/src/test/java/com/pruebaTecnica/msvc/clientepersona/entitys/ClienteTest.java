package com.pruebaTecnica.msvc.clientepersona.entitys;
import com.pruebaTecnica.msvc.clientepersona.models.Cuenta;
import com.pruebaTecnica.msvc.clientepersona.models.entitys.Cliente;
import com.pruebaTecnica.msvc.clientepersona.models.entitys.ClienteCuenta;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class ClienteTest {
    private Cliente cliente;

    @Mock
    private Cuenta cuenta;

    @Mock
    private ClienteCuenta clienteCuenta;

    @Before
    public void setUp() {
        cliente = new Cliente();
    }

    @Test
    public void testAddClienteCuenta() {
        assertTrue(cliente.getClienteCuentas().isEmpty());

        cliente.addClienteCuenta(clienteCuenta);

        assertEquals(1, cliente.getClienteCuentas().size());
        assertTrue(cliente.getClienteCuentas().contains(clienteCuenta));
    }

    @Test
    public void testRemoveClienteCuenta() {
        // Agrega una cuenta al cliente
        cliente.addClienteCuenta(clienteCuenta);

        // Verifica que la cuenta se haya agregado
        assertEquals(1, cliente.getClienteCuentas().size());

        // Remueve la cuenta del cliente
        cliente.removeClienteCuenta(clienteCuenta);

        // Verifica que la cuenta se haya removido
        assertTrue(cliente.getClienteCuentas().isEmpty());
    }

    @Test
    public void testSetCuentas() {
        // Crea una lista
        List<Cuenta> cuentas = new ArrayList<>();
        Cuenta cuenta1 = new Cuenta();
        Cuenta cuenta2 = new Cuenta();
        cuentas.add(cuenta1);
        cuentas.add(cuenta2);

        // Establece la lista de cuentas en el cliente
        cliente.setCuentas(cuentas);

        // Verifica que las cuentas se hayan establecido correctamente
        assertEquals(2, cliente.getCuentas().size());
        assertTrue(cliente.getCuentas().contains(cuenta1));
        assertTrue(cliente.getCuentas().contains(cuenta2));
    }
}
