package com.escola.client.service.impl;

import com.escola.client.model.entity.Contrato;
import com.escola.client.model.request.ContratoRequest;
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
    @Override
    public Contrato save(ContratoRequest request) {
        return null;
    }

    @Override
    public Optional<Page<Contrato>> findByFiltro(String filtro, Pageable pageable) {
        return Optional.empty();
    }

    @Override
    public Optional<Contrato> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Optional<Void> deleteById(Integer id) {
        return Optional.empty();
    }
}
