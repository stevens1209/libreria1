package com.distribuida.dao;

import com.distribuida.model.Factura;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback(value = false)

public class FacturaRepositorioTestIntegracion {
    @Autowired
    private FacturaRepositorio facturaRepositorio;

    @Test
    public void findAll(){
        List<Factura> facturas = facturaRepositorio.findAll();
        for(Factura item : facturas){
            System.out.println(item.toString());
        }

    }


}
