package com.example.api_pedidos.model;

public class PedidoItem {
    private Long id;
    private Long produtoId;
    private Integer quantidade;
    private Double precoUnit;

    public Double getSubtotal() {
        if (precoUnit == null || quantidade == null) return 0.0;
        return precoUnit * quantidade;
    }

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProdutoId() { return produtoId; }
    public void setProdutoId(Long produtoId) { this.produtoId = produtoId; }

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

    public Double getPrecoUnit() { return precoUnit; }
    public void setPrecoUnit(Double precoUnit) { this.precoUnit = precoUnit; }
}
