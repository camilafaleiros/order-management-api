package com.example.api_pedidos.controller;

import com.example.api_pedidos.dto.pedido.*;
import com.example.api_pedidos.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    // ---------- LISTAR (body enxuto + paginação nos headers) ----------
    @GetMapping
    public ResponseEntity<List<PedidoDTO>> listar(
            Pageable pageable,
            @RequestParam(required = false) Long clienteId,
            @RequestParam(required = false) String status
    ) {
        Page<PedidoDTO> page = service.listar(pageable, clienteId, status);
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
    public ResponseEntity<PedidoDTO> criar(@Valid @RequestBody PedidoCreateDTO body) {
        PedidoDTO dto = service.criar(body);
        return ResponseEntity.created(URI.create("/pedidos/" + dto.id())).body(dto);
    }

    // ---------- DETALHAR ----------
    @GetMapping("/{id}")
    public PedidoDTO detalhar(@PathVariable Long id) {
        return service.detalhar(id);
    }

    // ---------- ATUALIZAR (corpo igual ao create) ----------
    @PutMapping("/{id}")
    public PedidoDTO atualizar(@PathVariable Long id, @Valid @RequestBody PedidoCreateDTO body) {
        return service.atualizar(id, body);
    }

    // ---------- ATUALIZAR STATUS ----------
    @PatchMapping("/{id}/status")
    public PedidoDTO atualizarStatus(@PathVariable Long id, @Valid @RequestBody PedidoStatusPatchDTO body) {
        return service.atualizarStatus(id, body);
    }

    // ---------- DELETAR ----------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // ---------- ITENS DO PEDIDO ----------
    @GetMapping("/{id}/itens")
    public List<PedidoItemDTO> listarItens(@PathVariable Long id) {
        return service.listarItens(id);
    }

    @PostMapping("/{id}/itens")
    public PedidoItemDTO adicionarItem(@PathVariable Long id, @Valid @RequestBody PedidoItemCreateDTO body) {
        return service.adicionarItem(id, body);
    }

    @DeleteMapping("/{id}/itens/{itemId}")
    public ResponseEntity<Void> removerItem(@PathVariable Long id, @PathVariable Long itemId) {
        service.removerItem(id, itemId);
        return ResponseEntity.noContent().build();
    }
}
