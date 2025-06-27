package com.perfulandiaspa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandiaspa.model.Producto;
import com.perfulandiaspa.repository.ProductoRepository;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    public Producto crearProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> buscarProductoPorId(Long id) {
        return productoRepository.findById(id);
    }

    public Producto actualizarProducto(long id, Producto productoAct) {
        return productoRepository.findById(id).map(producto -> {
            producto.setNombre(productoAct.getNombre());
            producto.setTipo(productoAct.getTipo());
            producto.setPrecio(productoAct.getPrecio());
            producto.setMililitros(productoAct.getMililitros());
            producto.setStock(productoAct.getStock());
            producto.setCodigo(productoAct.getCodigo());
            return productoRepository.save(producto);
        }).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        };
    
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }
}

