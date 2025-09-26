package com.example.api_pedidos.repository;

import com.example.api_pedidos.model.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository {
    Cliente save(Cliente c);
    Optional<Cliente> findById(Long id);
    void deleteById(Long id);
    List<Cliente> findAll();
}
