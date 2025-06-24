package com.distribuida.controller;
import com.distribuida.model.FacturaDetalle;
import com.distribuida.service.FacturaDetalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas-detalle")
public class FacturaDetalleController {

    @Autowired
    private FacturaDetalleService facturaDetalleService;

    @GetMapping
    public ResponseEntity<List<FacturaDetalle>> findAll() {
        List<FacturaDetalle> detalles = facturaDetalleService.findAll();
        return ResponseEntity.ok(detalles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacturaDetalle> findOne(@PathVariable int id) {
        FacturaDetalle detalle = facturaDetalleService.findOne(id);
        return ResponseEntity.ok(detalle);
    }

    @PostMapping
    public ResponseEntity<FacturaDetalle> save(@RequestBody FacturaDetalle detalle) {
        FacturaDetalle detalleGuardado = facturaDetalleService.save(detalle);
        return ResponseEntity.ok(detalleGuardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FacturaDetalle> update(@PathVariable int id, @RequestBody FacturaDetalle detalle) {
        FacturaDetalle detalleActualizado = facturaDetalleService.update(id, detalle);
        if (detalleActualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(detalleActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        facturaDetalleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}