package com.perfulandiaspa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.perfulandiaspa.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long>{
    Optional<Producto> findByCodigo(String codigo);
    List<Producto> findByTipoAndIdNot(String tipo, Long id);
}
