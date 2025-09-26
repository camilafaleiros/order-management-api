package com.example.api_pedidos.model;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private Long id;
    private Long clienteId;
    private PedidoStatus status = PedidoStatus.ABERTO;
    private List<PedidoItem> itens = new ArrayList<>();
    private String observacoes;
    private OffsetDateTime criadoEm = OffsetDateTime.now();

    public Double getTotal() {
        return itens.stream().mapToDouble(PedidoItem::getSubtotal).sum();
    }

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public PedidoStatus getStatus() { return status; }
    public void setStatus(PedidoStatus status) { this.status = status; }

    public List<PedidoItem> getItens() { return itens; }
    public void setItens(List<PedidoItem> itens) { this.itens = itens; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public OffsetDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(OffsetDateTime criadoEm) { this.criadoEm = criadoEm; }
}
