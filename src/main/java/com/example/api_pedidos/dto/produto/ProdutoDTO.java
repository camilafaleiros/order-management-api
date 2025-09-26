package com.example.api_pedidos.dto.produto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record ProdutoDTO(
        Long id,
        String nome,
        String sku,
        BigDecimal preco,
        Integer estoque,
        Boolean ativo,
        OffsetDateTime criadoEm
) {}
