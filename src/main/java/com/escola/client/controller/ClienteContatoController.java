package com.escola.client.controller;

import com.escola.client.model.mapper.ClienteContatoMapper;
import com.escola.client.model.request.ClienteContatoRequest;
import com.escola.client.model.response.ClienteContatoResponse;
import com.escola.client.service.ClienteContatoService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClienteContatoController {

    ClienteContatoMapper mapper;
    ClienteContatoService service;

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public Optional<ClienteContatoResponse> fetchContatoById(@Argument Integer id) {
        return service.findById(id).map(mapper::toOutput);
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public Optional<List<ClienteContatoResponse>> fetchContatoByIdCliente(@Argument Integer id) {
        return service.findAllByClienteId(id).map(mapper::toOutputList);
    }

    @MutationMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public boolean deleteContatoById(@Argument Integer id) {
        var entity = service.apagar(id);
        return entity.orElse(false);
    }

    @MutationMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ClienteContatoResponse saveClienteContato(@Argument ClienteContatoRequest request) {
        var entity = service.save(request);
        return mapper.toOutput(entity);
    }

}
