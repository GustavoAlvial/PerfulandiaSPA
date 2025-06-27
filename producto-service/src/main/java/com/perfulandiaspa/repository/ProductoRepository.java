package com.perfulandiaspa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.perfulandiaspa.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long>{
    Optional<Producto> findByCodigo(String codigo);
}
