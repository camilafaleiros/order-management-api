package com.example.api_pedidos.dto.produto;

import jakarta.validation.constraints.NotNull;

public record EstoquePatchDTO(
        @NotNull Integer delta
) {}
