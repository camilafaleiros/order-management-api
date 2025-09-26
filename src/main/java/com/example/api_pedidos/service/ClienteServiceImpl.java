package com.example.api_pedidos.service;

import com.example.api_pedidos.dto.cliente.ClienteCreateDTO;
import com.example.api_pedidos.dto.cliente.ClienteDTO;
import com.example.api_pedidos.exception.NotFoundException;
import com.example.api_pedidos.model.Cliente;
import com.example.api_pedidos.repository.ClienteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository repo;

    public ClienteServiceImpl(ClienteRepository repo) {
        this.repo = repo;
    }

    @Override
    public Page<ClienteDTO> listar(Pageable pageable, String nome, String email, String cpfCnpj) {
        List<Cliente> filtrados = repo.findAll().stream()
                .filter(c -> nome == null || (c.getNome() != null && c.getNome().toLowerCase().contains(nome.toLowerCase())))
                .filter(c -> email == null || Objects.equals(email, c.getEmail()))
                .filter(c -> cpfCnpj == null || Objects.equals(cpfCnpj, c.getCpfCnpj()))
                .toList();

        List<ClienteDTO> dtos = filtrados.stream().map(this::toDTO).toList();
        return new PageImpl<>(dtos, pageable, dtos.size());
    }

    @Override
    public ClienteDTO criar(ClienteCreateDTO dto) {
        Cliente c = new Cliente();
        c.setNome(dto.nome());
        c.setEmail(dto.email());
        c.setCpfCnpj(dto.cpfCnpj());
        c.setAtivo(true);
        return toDTO(repo.save(c));
    }

    @Override
    public ClienteDTO detalhar(Long id) {
        return toDTO(buscar(id));
    }

    @Override
    public ClienteDTO atualizar(Long id, ClienteCreateDTO dto) {
        Cliente c = buscar(id);
        c.setNome(dto.nome());
        c.setEmail(dto.email());
        c.setCpfCnpj(dto.cpfCnpj());
        return toDTO(repo.save(c));
    }

    @Override
    public void deletar(Long id) {
        buscar(id);
        repo.deleteById(id);
    }

    private Cliente buscar(Long id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Cliente não encontrado"));
    }

    private ClienteDTO toDTO(Cliente c) {
        return new ClienteDTO(c.getId(), c.getNome(), c.getEmail(), c.getCpfCnpj(), c.getAtivo(), c.getCriadoEm());
    }
}
