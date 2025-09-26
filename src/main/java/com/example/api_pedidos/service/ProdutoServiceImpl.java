package com.example.api_pedidos.service;

import com.example.api_pedidos.dto.produto.ProdutoCreateDTO;
import com.example.api_pedidos.dto.produto.ProdutoDTO;
import com.example.api_pedidos.exception.NotFoundException;
import com.example.api_pedidos.model.Produto;
import com.example.api_pedidos.repository.ProdutoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository repo;

    public ProdutoServiceImpl(ProdutoRepository repo) {
        this.repo = repo;
    }

    @Override
    public Page<ProdutoDTO> listar(Pageable pageable, String nome, String sku, Boolean ativo, Double minPreco, Double maxPreco) {
        List<Produto> filtrados = repo.findAll().stream()
                .filter(p -> nome == null || (p.getNome() != null && p.getNome().toLowerCase().contains(nome.toLowerCase())))
                .filter(p -> sku == null || Objects.equals(sku, p.getSku()))
                .filter(p -> ativo == null || Objects.equals(ativo, p.getAtivo()))
                .filter(p -> minPreco == null || (p.getPreco() != null && p.getPreco().compareTo(BigDecimal.valueOf(minPreco)) >= 0))
                .filter(p -> maxPreco == null || (p.getPreco() != null && p.getPreco().compareTo(BigDecimal.valueOf(maxPreco)) <= 0))
                .toList();

        List<ProdutoDTO> dtos = filtrados.stream().map(this::toDTO).toList();
        return new PageImpl<>(dtos, pageable, dtos.size());
    }

    @Override
    public ProdutoDTO criar(ProdutoCreateDTO dto) {
        Produto p = new Produto();
        p.setNome(dto.nome());
        p.setSku(dto.sku());
        p.setPreco(dto.preco());
        p.setEstoque(dto.estoque());
        p.setAtivo(dto.ativo());
        return toDTO(repo.save(p));
    }

    @Override
    public ProdutoDTO detalhar(Long id) {
        return toDTO(buscar(id));
    }

    @Override
    public ProdutoDTO atualizar(Long id, ProdutoCreateDTO dto) {
        Produto p = buscar(id);
        p.setNome(dto.nome());
        p.setSku(dto.sku());
        p.setPreco(dto.preco());
        p.setEstoque(dto.estoque());
        p.setAtivo(dto.ativo());
        return toDTO(repo.save(p));
    }

    @Override
    public ProdutoDTO ajustarEstoque(Long id, Integer delta) {
        Produto p = buscar(id);
        int atual = p.getEstoque() == null ? 0 : p.getEstoque();
        p.setEstoque(atual + delta);
        return toDTO(repo.save(p));
    }

    @Override
    public void deletar(Long id) {
        buscar(id);
        repo.deleteById(id);
    }

    private Produto buscar(Long id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Produto não encontrado"));
    }

    private ProdutoDTO toDTO(Produto p) {
        return new ProdutoDTO(p.getId(), p.getNome(), p.getSku(), p.getPreco(), p.getEstoque(), p.getAtivo(), p.getCriadoEm());
    }
}
