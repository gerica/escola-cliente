package com.escola.client.service;

import com.escola.client.model.entity.Cliente;
import com.escola.client.model.request.ClienteRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ClienteService {
    Cliente save(ClienteRequest request);

    Optional<Page<Cliente>> findByFiltro(String filtro, Pageable pageable);

    Optional<Cliente> findById(Integer id);
}
