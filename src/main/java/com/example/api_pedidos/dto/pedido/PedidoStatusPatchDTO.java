package com.example.api_pedidos.dto.pedido;

import com.example.api_pedidos.model.PedidoStatus;

public record PedidoStatusPatchDTO(
        PedidoStatus status
) {}
