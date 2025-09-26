package com.example.api_pedidos.dto.produto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record ProdutoCreateDTO(
        @NotBlank String nome,
        @NotBlank String sku,
        @NotNull @Positive BigDecimal preco,
        @NotNull Integer estoque,
        @NotNull Boolean ativo
) {}
