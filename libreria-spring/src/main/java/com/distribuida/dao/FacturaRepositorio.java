package com.distribuida.dao;

import com.distribuida.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepositorio extends JpaRepository<Factura,Integer> {


}
