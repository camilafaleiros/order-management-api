package com.example.api_pedidos.repository;

import com.example.api_pedidos.model.Cliente;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryClienteRepository implements ClienteRepository {

    private final Map<Long, Cliente> db = new ConcurrentHashMap<>();
    private final AtomicLong seq = new AtomicLong(0);

    @Override
    public Cliente save(Cliente c) {
        if (c.getId() == null) c.setId(seq.incrementAndGet());
        db.put(c.getId(), c);
        return c;
    }

    @Override
    public Optional<Cliente> findById(Long id) {
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public void deleteById(Long id) {
        db.remove(id);
    }

    @Override
    public List<Cliente> findAll() {
        return new ArrayList<>(db.values());
    }
}
