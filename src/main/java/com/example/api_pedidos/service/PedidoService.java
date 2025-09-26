package com.example.api_pedidos.service;

import com.example.api_pedidos.dto.pedido.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PedidoService {
    Page<PedidoDTO> listar(Pageable pageable, Long clienteId, String status);
    PedidoDTO criar(PedidoCreateDTO dto);
    PedidoDTO detalhar(Long id);
    PedidoDTO atualizar(Long id, PedidoCreateDTO dto);
    PedidoDTO atualizarStatus(Long id, PedidoStatusPatchDTO dto);
    void deletar(Long id);
    List<PedidoItemDTO> listarItens(Long id);
    PedidoItemDTO adicionarItem(Long id, PedidoItemCreateDTO dto);
    void removerItem(Long id, Long itemId);
}
