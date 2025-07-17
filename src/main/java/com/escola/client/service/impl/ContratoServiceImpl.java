package com.escola.client.service.impl;

import com.escola.client.model.entity.Contrato;
import com.escola.client.model.mapper.ContratoMapper;
import com.escola.client.model.request.ContratoRequest;
import com.escola.client.model.response.ParametroResponse;
import com.escola.client.repository.ContratoRepository;
import com.escola.client.service.ContratoService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class ContratoServiceImpl implements ContratoService {

    ContratoRepository repository;
    ContratoMapper mapper;
    HttpGraphQlClient graphQlClient;

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

    @Override
    public Optional<Contrato> parseContrato(Long idContrato) {
        String query = """
                query findByChave($chave: String!) {
                    findByChave(chave: $chave) {
                        id
                        chave
                        modeloContrato
                    }
                }
                """;
        try {
            ParametroResponse response = graphQlClient.document(query)
                    .variable("chave", CHAVE_CONTRATO_MODELO_PADRAO)
                    .retrieve("findByChave")
                    .toEntity(ParametroResponse.class)
                    .block();
            Optional<Contrato> optional = findById(idContrato);
            if (optional.isPresent() && response != null) {
                optional.get().setContratoDoc(response.getModeloContrato());
            }
            return optional;
        } catch (Exception e) {
            log.error("Erro ao chamar o admin-service: {}", e.getMessage());
            return Optional.empty();
        }
    }


    private String getCurrentRequestToken() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes attributes) {
            return attributes.getRequest().getHeader(HttpHeaders.AUTHORIZATION);
        }
        return null;
    }

}
