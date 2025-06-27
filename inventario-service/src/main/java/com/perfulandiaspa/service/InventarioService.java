package com.perfulandiaspa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.perfulandiaspa.model.Inventario;
import com.perfulandiaspa.repository.InventarioRepository;

@Service
public class InventarioService {
    private final InventarioRepository inventarioRepository;
    
    public InventarioService(InventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    public Inventario crearInventario(Inventario inventario) {
        return inventarioRepository.save(inventario);
    }

    public List<Inventario> listarInventario() {
        return inventarioRepository.findAll();
    }

    public Optional<Inventario> buscarInventarioPorId(Long id) {
        return inventarioRepository.findById(id);
    }

    public Inventario actualizarInventario(Long id, Inventario inventarioAct) {
        return inventarioRepository.findById(id).map(inventario -> {
            inventario.setProductoId(inventarioAct.getProductoId());
            inventario.setCantidad(inventarioAct.getCantidad());
            return inventarioRepository.save(inventario);
        }).orElseThrow(() -> new RuntimeException("Inventario no encontrado"));
    }

    public void eliminarInventario(Long id) {
        inventarioRepository.deleteById(id);
    }
}
