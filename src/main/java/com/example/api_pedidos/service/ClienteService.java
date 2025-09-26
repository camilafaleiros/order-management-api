package com.example.api_pedidos.service;

import com.example.api_pedidos.dto.cliente.ClienteCreateDTO;
import com.example.api_pedidos.dto.cliente.ClienteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClienteService {
    Page<ClienteDTO> listar(Pageable pageable, String nome, String email, String cpfCnpj);
    ClienteDTO criar(ClienteCreateDTO dto);
    ClienteDTO detalhar(Long id);
    ClienteDTO atualizar(Long id, ClienteCreateDTO dto);
    void deletar(Long id);
}
