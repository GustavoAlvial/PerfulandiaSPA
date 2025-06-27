package com.perfulandiaspa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.perfulandiaspa.model.Venta;
import com.perfulandiaspa.repository.VentaRepository;

@Service
public class VentaService {
    private final VentaRepository ventaRepository;

    public VentaService(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }

    public Optional<Venta> buscarVentaPorId(Long id) {
        return ventaRepository.findById(id);
    }

    public Venta crearVenta(Venta venta) {
        if (venta.getDetalles() != null) {
            venta.getDetalles().forEach(detalle -> detalle.setVenta(venta));
        }
        return ventaRepository.save(venta);
    }

    public Venta actualizarVenta(Long id, Venta ventaActualizada) {
        return ventaRepository.findById(id).map(venta -> {
            venta.setFecha(ventaActualizada.getFecha());
            venta.setEstado(ventaActualizada.getEstado());
            venta.setTotal(ventaActualizada.getTotal());
            if (ventaActualizada.getDetalles() != null) {
                venta.getDetalles().clear();
                ventaActualizada.getDetalles().forEach(detalle -> detalle.setVenta(venta));
                venta.getDetalles().addAll(ventaActualizada.getDetalles());
            }
            return ventaRepository.save(venta);
        }).orElseThrow(() -> new RuntimeException("Venta no encontrada"));
    }

    public void eliminarVenta(Long id) {
        ventaRepository.deleteById(id);
    }
}
