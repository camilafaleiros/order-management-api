package com.example.api_pedidos.repository;

import com.example.api_pedidos.model.Pedido;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryPedidoRepository implements PedidoRepository {

    private final Map<Long, Pedido> db = new ConcurrentHashMap<>();
    private final AtomicLong seq = new AtomicLong(0);

    @Override
    public Pedido save(Pedido p) {
        if (p.getId() == null) p.setId(seq.incrementAndGet());
        db.put(p.getId(), p);
        return p;
    }

    @Override
    public Optional<Pedido> findById(Long id) {
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public void deleteById(Long id) {
        db.remove(id);
    }

    @Override
    public List<Pedido> findAll() {
        return new ArrayList<>(db.values());
    }
}
