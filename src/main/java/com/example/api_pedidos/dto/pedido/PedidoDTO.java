package com.example.api_pedidos.dto.pedido;

import com.example.api_pedidos.model.PedidoStatus;
import java.time.OffsetDateTime;
import java.util.List;

public record PedidoDTO(
        Long id,
        Long clienteId,
        PedidoStatus status,
        List<PedidoItemDTO> itens,
        Double total,
        String observacoes,
        OffsetDateTime criadoEm
) {}
