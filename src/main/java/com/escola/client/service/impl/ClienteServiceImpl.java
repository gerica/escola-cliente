package com.escola.client.service.impl;

import com.escola.client.model.entity.Cliente;
import com.escola.client.model.mapper.ClienteMapper;
import com.escola.client.model.request.ClienteRequest;
import com.escola.client.repository.ClienteRepository;
import com.escola.client.service.ClienteService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    ClienteRepository clienteRepository;
    ClienteMapper clienteMapper;

    @Override
    public Cliente save(ClienteRequest request) {
        Cliente entity;
        Optional<Cliente> optional = Optional.empty();
        if (request.id() != null) {
            optional = clienteRepository.findById(request.id());
        }

        if (optional.isPresent()) {
            entity = clienteMapper.updateEntity(request, optional.get());
        } else {
            entity = clienteMapper.toEntity(request);
            entity.setDataCadastro(LocalDateTime.now());
        }
        entity.setDataAtualizacao(LocalDateTime.now());
        return clienteRepository.save(entity);

    }

    @Override
    public Optional<Page<Cliente>> findByFiltro(String filtro, Pageable pageable) {
        return clienteRepository.findByFiltro(filtro, pageable);
    }

    @Override
    public Optional<Cliente> findById(Integer id) {
        return clienteRepository.findById(id);
    }
}
