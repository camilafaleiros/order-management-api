package com.example.api_pedidos.util;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;

public final class PaginationUtil {
    private PaginationUtil() {}
    public static HttpHeaders build(Page<?> page) {
        HttpHeaders h = new HttpHeaders();
        h.add("X-Total-Count", String.valueOf(page.getTotalElements()));
        h.add("X-Total-Pages", String.valueOf(page.getTotalPages()));
        h.add("X-Page-Number", String.valueOf(page.getNumber()));
        h.add("X-Page-Size", String.valueOf(page.getSize()));
        return h;
    }
}
