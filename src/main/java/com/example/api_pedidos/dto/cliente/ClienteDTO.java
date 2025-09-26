package com.example.api_pedidos.dto.cliente;

import java.time.OffsetDateTime;

public record ClienteDTO(
        Long id,
        String nome,
        String email,
        String cpfCnpj,
        Boolean ativo,
        OffsetDateTime criadoEm
) {}
