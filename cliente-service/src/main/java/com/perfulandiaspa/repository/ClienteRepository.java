package com.perfulandiaspa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.perfulandiaspa.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
