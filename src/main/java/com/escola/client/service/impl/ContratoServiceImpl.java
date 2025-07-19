package com.escola.client.service.impl;

import com.escola.client.model.entity.Contrato;
import com.escola.client.model.mapper.ContratoMapper;
import com.escola.client.model.request.ContratoRequest;
import com.escola.client.model.response.ParametroResponse;
import com.escola.client.repository.ContratoRepository;
import com.escola.client.service.ArtificalInteligenceService;
import com.escola.client.service.ContratoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

@Service()
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class ContratoServiceImpl implements ContratoService {

    ContratoRepository repository;
    ContratoMapper mapper;
    HttpGraphQlClient graphQlClient;
    ArtificalInteligenceService gemini;

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

                // Criar um ObjectMapper
                ObjectMapper objectMapper = new ObjectMapper();

                // Registrar o módulo JavaTimeModule para lidar com LocalDate e LocalDateTime
                objectMapper.registerModule(new JavaTimeModule());
                // Opcional: Para formatar datas como "yyyy-MM-dd" e "yyyy-MM-ddTHH:mm:ss"
                objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

                // Converter o objeto Contrato para uma string JSON
                String jsonOutput = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(optional.get());

                var resultSmartContrato = gemini.generateText("Estou passando para você um contrato: " + response.getModeloContrato() +
                        "E aqui está os dados de um cliente: " + jsonOutput + ", preencha os campos referente ao cliente nesse contrato e " +
                        "me retorno o contrato, no mesmo formato que te enviei.");
                optional.get().setContratoDoc(resultSmartContrato);
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
