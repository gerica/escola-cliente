package com.escola.client.service;

import com.escola.client.model.entity.Contrato;
import com.escola.client.model.request.ContratoRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ContratoService {
    String CHAVE_CONTRATO_MODELO_PADRAO = "CHAVE_CONTRATO_MODELO_PADRAO";

    Contrato save(ContratoRequest request);

    Optional<Page<Contrato>> findByFiltro(String filtro, Pageable pageable);

    Optional<Contrato> findById(Long id);

    Optional<Void> deleteById(Integer id);

    Optional<Contrato> parseContrato(Long idContrato);
}
