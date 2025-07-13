package com.escola.client.controller;

import com.escola.client.controller.help.PageableHelp;
import com.escola.client.controller.help.SortInput;
import com.escola.client.model.entity.Cliente;
import com.escola.client.model.mapper.ClienteMapper;
import com.escola.client.model.request.ClienteRequest;
import com.escola.client.model.response.ClienteResponse;
import com.escola.client.service.ClienteService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
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
public class ClienteController {

    ClienteMapper clienteMapper;
    ClienteService clienteService;
    PageableHelp pageableHelp;

    @MutationMapping
    @PreAuthorize("isAuthenticated()")
    public ClienteResponse saveCliente(@Argument ClienteRequest request) {
        var entity = clienteService.save(request);
        return clienteMapper.toOutput(entity);
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public Page<ClienteResponse> fetchAllClientes(
            @Argument String filtro,
            @Argument Optional<Integer> page, // Optional to handle default values from schema
            @Argument Optional<Integer> size, // Optional to handle default values from schema
            @Argument Optional<List<SortInput>> sort // Optional for sorting
    ) {

        Page<Cliente> entities = clienteService.findByFiltro(filtro, pageableHelp.getPageable(page, size, sort)).orElse(Page.empty());
        return entities.map(clienteMapper::toOutput);
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public Optional<ClienteResponse> fetchByIdCliente(@Argument Integer id) {
        return clienteService.findById(id).map(clienteMapper::toOutput);
    }

}
