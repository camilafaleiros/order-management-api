package com.example.api_pedidos.service;

import com.example.api_pedidos.dto.pedido.*;
import com.example.api_pedidos.exception.NotFoundException;
import com.example.api_pedidos.model.Pedido;
import com.example.api_pedidos.model.PedidoItem;
import com.example.api_pedidos.repository.PedidoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository repo;
    private final AtomicLong seqItem = new AtomicLong(0);

    public PedidoServiceImpl(PedidoRepository repo) {
        this.repo = repo;
    }

    @Override
    public Page<PedidoDTO> listar(Pageable pageable, Long clienteId, String statusStr) {
        List<Pedido> all = repo.findAll().stream()
                .filter(p -> clienteId == null || clienteId.equals(p.getClienteId()))
                .filter(p -> statusStr == null || p.getStatus().name().equalsIgnoreCase(statusStr))
                .toList();

        List<PedidoDTO> dtos = all.stream().map(this::toDTO).toList();
        return new PageImpl<>(dtos, pageable, dtos.size());
    }

    @Override
    public PedidoDTO criar(PedidoCreateDTO dto) {
        Pedido p = new Pedido();
        p.setClienteId(dto.clienteId());
        p.setObservacoes(dto.observacoes());

        if (dto.itens() != null) {
            dto.itens().forEach(i -> {
                PedidoItem item = new PedidoItem();
                item.setId(seqItem.incrementAndGet());
                item.setProdutoId(i.produtoId());
                item.setQuantidade(i.quantidade());
                item.setPrecoUnit(10.0); // mock para teste; integra com Produto depois
                p.getItens().add(item);
            });
        }
        return toDTO(repo.save(p));
    }

    @Override
    public PedidoDTO detalhar(Long id) {
        return toDTO(buscar(id));
    }

    @Override
    public PedidoDTO atualizar(Long id, PedidoCreateDTO dto) {
        Pedido p = buscar(id);
        p.setClienteId(dto.clienteId());
        p.setObservacoes(dto.observacoes());
        // (se quiser, atualize itens aqui futuramente)
        return toDTO(repo.save(p));
    }

    @Override
    public PedidoDTO atualizarStatus(Long id, PedidoStatusPatchDTO dto) {
        Pedido p = buscar(id);
        p.setStatus(dto.status());
        return toDTO(repo.save(p));
    }

    @Override
    public void deletar(Long id) {
        buscar(id);
        repo.deleteById(id);
    }

    @Override
    public List<PedidoItemDTO> listarItens(Long id) {
        Pedido p = buscar(id);
        return p.getItens().stream().map(this::toItemDTO).toList();
    }

    @Override
    public PedidoItemDTO adicionarItem(Long id, PedidoItemCreateDTO dto) {
        Pedido p = buscar(id);
        PedidoItem item = new PedidoItem();
        item.setId(seqItem.incrementAndGet());
        item.setProdutoId(dto.produtoId());
        item.setQuantidade(dto.quantidade());
        item.setPrecoUnit(10.0);
        p.getItens().add(item);
        repo.save(p);
        return toItemDTO(item);
    }

    @Override
    public void removerItem(Long id, Long itemId) {
        Pedido p = buscar(id);
        p.getItens().removeIf(it -> it.getId().equals(itemId));
        repo.save(p);
    }

    private Pedido buscar(Long id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Pedido não encontrado"));
    }

    private PedidoDTO toDTO(Pedido p) {
        return new PedidoDTO(
                p.getId(),
                p.getClienteId(),
                p.getStatus(),
                p.getItens().stream().map(this::toItemDTO).toList(),
                p.getTotal(),
                p.getObservacoes(),
                p.getCriadoEm()
        );
    }

    private PedidoItemDTO toItemDTO(PedidoItem item) {
        return new PedidoItemDTO(item.getId(), item.getProdutoId(), item.getQuantidade(),
                item.getPrecoUnit(), item.getSubtotal());
    }
}
