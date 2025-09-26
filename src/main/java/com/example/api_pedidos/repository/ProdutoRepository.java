package com.example.api_pedidos.repository;

import com.example.api_pedidos.model.Produto;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository {
    Produto save(Produto p);
    Optional<Produto> findById(Long id);
    void deleteById(Long id);
    List<Produto> findAll();
}
