package com.example.api_pedidos.repository;

import com.example.api_pedidos.model.Pedido;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository {
    Pedido save(Pedido p);
    Optional<Pedido> findById(Long id);
    void deleteById(Long id);
    List<Pedido> findAll();
}
