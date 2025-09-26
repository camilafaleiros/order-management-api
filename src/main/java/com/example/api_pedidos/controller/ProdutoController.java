package com.example.api_pedidos.controller;

import com.example.api_pedidos.dto.produto.EstoquePatchDTO;
import com.example.api_pedidos.dto.produto.ProdutoCreateDTO;
import com.example.api_pedidos.dto.produto.ProdutoDTO;
import com.example.api_pedidos.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    // ---------- LISTAR  ----------
    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listar(
            Pageable pageable,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String sku,
            @RequestParam(required = false) Boolean ativo,
            @RequestParam(required = false) Double minPreco,
            @RequestParam(required = false) Double maxPreco
    ) {
        Page<ProdutoDTO> page = service.listar(pageable, nome, sku, ativo, minPreco, maxPreco);
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
    public ResponseEntity<ProdutoDTO> criar(@Valid @RequestBody ProdutoCreateDTO body) {
        ProdutoDTO criado = service.criar(body);
        return ResponseEntity.created(URI.create("/produtos/" + criado.id())).body(criado);
    }

    // ---------- DETALHAR ----------
    @GetMapping("/{id}")
    public ProdutoDTO detalhar(@PathVariable Long id) {
        return service.detalhar(id);
    }

    // ---------- ATUALIZAR ----------
    @PutMapping("/{id}")
    public ProdutoDTO atualizar(@PathVariable Long id, @Valid @RequestBody ProdutoCreateDTO body) {
        return service.atualizar(id, body);
    }

    // ---------- AJUSTAR ESTOQUE ----------
    @PatchMapping("/{id}/estoque")
    public ProdutoDTO ajustarEstoque(@PathVariable Long id, @Valid @RequestBody EstoquePatchDTO body) {
        return service.ajustarEstoque(id, body.delta());
    }

    // ---------- DELETAR ----------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
