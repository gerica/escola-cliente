package com.escola.client.service.impl;

import com.escola.client.model.entity.Contrato;
import com.escola.client.model.mapper.ContratoMapper;
import com.escola.client.model.request.ContratoRequest;
import com.escola.client.repository.ContratoRepository;
import com.escola.client.service.ContratoService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ContratoServiceImpl implements ContratoService {

    ContratoRepository repository;
    ContratoMapper mapper;

    @Override
    public Contrato save(ContratoRequest request) {
        Contrato entity;
        Optional<Contrato> optional = Optional.empty();
        if (request.idContrato() != null) {
            optional = repository.findById(request.idContrato());
        }

        if (optional.isPresent()) {
            entity = mapper.updateEntity(request, optional.get());
        } else {
            entity = mapper.toEntity(request);
//            entity.setdata(LocalDateTime.now());
        }
//        entity.setDataAtualizacao(LocalDateTime.now());
        return repository.save(entity);
    }

    @Override
    public Optional<Page<Contrato>> findByFiltro(String filtro, Pageable pageable) {
        return repository.findByFiltro(filtro, pageable);
    }

    @Override
    public Optional<Contrato> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Void> deleteById(Integer id) {
        return Optional.empty();
    }
}
