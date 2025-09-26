package com.example.api_pedidos.dto.pedido;

public record PedidoItemCreateDTO(
        Long produtoId,
        Integer quantidade
) {}
