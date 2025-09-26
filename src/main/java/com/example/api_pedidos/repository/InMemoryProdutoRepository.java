package com.example.api_pedidos.repository;

import com.example.api_pedidos.model.Produto;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryProdutoRepository implements ProdutoRepository {

    private final Map<Long, Produto> db = new ConcurrentHashMap<>();
    private final AtomicLong seq = new AtomicLong(0);

    @Override
    public Produto save(Produto p) {
        if (p.getId() == null) p.setId(seq.incrementAndGet());
        db.put(p.getId(), p);
        return p;
    }

    @Override
    public Optional<Produto> findById(Long id) {
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public void deleteById(Long id) {
        db.remove(id);
    }

    @Override
    public List<Produto> findAll() {
        return new ArrayList<>(db.values());
    }
}
