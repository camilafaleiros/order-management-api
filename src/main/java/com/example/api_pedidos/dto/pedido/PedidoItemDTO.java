package com.example.api_pedidos.dto.pedido;

public record PedidoItemDTO(
        Long id,
        Long produtoId,
        Integer quantidade,
        Double precoUnit,
        Double subtotal
) {}
