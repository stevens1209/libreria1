package com.distribuida.controller;

import com.distribuida.model.Cliente;
import com.distribuida.model.Factura;
import com.distribuida.service.FacturaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FacturaController.class)
public class FacturaControllerTestIntegracion {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacturaService facturaService;

    @Autowired
    private ObjectMapper objectMapper;

    private Cliente crearClienteEjemplo() {
        return new Cliente(1, "1726695396", "Steven", "Simbana", "Lumbisi", "0984241253", "stevens@email.com");
    }

    private Factura crearFacturaEjemplo() {
        Factura factura = new Factura();
        factura.setIdFactura(1);
        factura.setNumFactura("F001-0001");
        factura.setFecha(new Date());
        factura.setTotalNeto(80.0);
        factura.setIva(20.0);
        factura.setTotal(100.0);
        factura.setCliente(crearClienteEjemplo());
        return factura;
    }

    @Test
    public void testFindAll() throws Exception {
        Factura factura = crearFacturaEjemplo();
        Mockito.when(facturaService.findAll()).thenReturn(List.of(factura));

        mockMvc.perform(get("/api/facturas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].numFactura").value("F001-0001"))
                .andExpect(jsonPath("$[0].cliente.nombre").value("Steven"));
    }

    @Test
    public void testSave() throws Exception {
        Factura factura = crearFacturaEjemplo();
        factura.setIdFactura(0); // simula que aún no tiene ID antes de guardar

        Mockito.when(facturaService.save(any(Factura.class))).thenReturn(factura);

        mockMvc.perform(post("/api/facturas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(factura)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numFactura").value("F001-0001"))
                .andExpect(jsonPath("$.cliente.nombre").value("Steven"));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/api/facturas/1"))
                .andExpect(status().isNoContent());
    }
}
