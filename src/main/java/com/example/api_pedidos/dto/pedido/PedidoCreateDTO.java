package com.example.api_pedidos.dto.pedido;

import java.util.List;

public record PedidoCreateDTO(
        Long clienteId,
        List<PedidoItemCreateDTO> itens,
        String observacoes
) {}
