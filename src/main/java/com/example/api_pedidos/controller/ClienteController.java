package com.example.api_pedidos.controller;

import com.example.api_pedidos.dto.cliente.ClienteCreateDTO;
import com.example.api_pedidos.dto.cliente.ClienteDTO;
import com.example.api_pedidos.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    // ---------- LISTAR (body enxuto + paginação nos headers) ----------
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listar(
            Pageable pageable,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String cpfCnpj
    ) {
        Page<ClienteDTO> page = service.listar(pageable, nome, email, cpfCnpj);
        return ResponseEntity.ok()
                .headers(buildPaginationHeaders(page))
                .body(page.getContent());
    }

    private HttpHeaders buildPaginationHeaders(Page<?> page) {
        HttpHeaders h = new HttpHeaders();
        h.add("X-Total-Count", String.valueOf(page.getTotalElements()));
        h.add("X-Total-Pages", String.valueOf(page.getTotalPages()));
        h.add("X-Page-Number", String.valueOf(page.getNumber()));
        h.add("X-Page-Size", String.valueOf(page.getSize()));
        return h;
    }

    // ---------- CRIAR ----------
    @PostMapping
    public ResponseEntity<ClienteDTO> criar(@Valid @RequestBody ClienteCreateDTO body) {
        ClienteDTO criado = service.criar(body);
        return ResponseEntity.created(URI.create("/clientes/" + criado.id())).body(criado);
    }

    // ---------- DETALHAR ----------
    @GetMapping("/{id}")
    public ClienteDTO detalhar(@PathVariable Long id) {
        return service.detalhar(id);
    }

    // ---------- ATUALIZAR ----------
    @PutMapping("/{id}")
    public ClienteDTO atualizar(@PathVariable Long id, @Valid @RequestBody ClienteCreateDTO body) {
        return service.atualizar(id, body);
    }

    // ---------- DELETAR ----------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
