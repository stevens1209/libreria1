package com.distribuida.service;

import com.distribuida.dao.FacturaDetalleRepositorio;
import com.distribuida.model.FacturaDetalle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacturaDetalleServiceImpl implements FacturaDetalleService {

    @Autowired
    private FacturaDetalleRepositorio facturaDetalleRepositorio;

    @Override
    public List<FacturaDetalle> findAll() {
        return facturaDetalleRepositorio.findAll();
    }

    @Override
    public FacturaDetalle findOne(int id) {
        Optional<FacturaDetalle> detalle = facturaDetalleRepositorio.findById(id);
        return detalle.orElse(null);
    }

    @Override
    public FacturaDetalle save(FacturaDetalle detalle) {
        return facturaDetalleRepositorio.save(detalle);
    }

    @Override
    public FacturaDetalle update(int id, FacturaDetalle detalle) {
        FacturaDetalle detalleExistente = findOne(id);
        if (detalleExistente == null) {
            return null;
        }

        detalleExistente.setCantidad(detalle.getCantidad());
        detalleExistente.setSubtotal(detalle.getSubtotal());
        detalleExistente.setLibro(detalle.getLibro());
        detalleExistente.setFactura(detalle.getFactura());

        return facturaDetalleRepositorio.save(detalleExistente);
    }

    @Override
    public void delete(int id) {
        if (facturaDetalleRepositorio.existsById(id)) {
            facturaDetalleRepositorio.deleteById(id);
        }
    }
}