package com.example.api_pedidos.dto.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClienteCreateDTO(
        @NotBlank @Size(min = 2, max = 120) String nome,
        @Email String email,
        @NotBlank @Size(min = 11, max = 14) String cpfCnpj
) {}
