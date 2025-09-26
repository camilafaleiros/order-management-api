package com.example.api_pedidos.service;

import com.example.api_pedidos.dto.produto.ProdutoCreateDTO;
import com.example.api_pedidos.dto.produto.ProdutoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProdutoService {
    Page<ProdutoDTO> listar(Pageable pageable, String nome, String sku, Boolean ativo, Double minPreco, Double maxPreco);
    ProdutoDTO criar(ProdutoCreateDTO dto);
    ProdutoDTO detalhar(Long id);
    ProdutoDTO atualizar(Long id, ProdutoCreateDTO dto);
    ProdutoDTO ajustarEstoque(Long id, Integer delta);
    void deletar(Long id);
}
