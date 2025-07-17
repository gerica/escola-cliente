package com.escola.client.controller;

import com.escola.client.controller.help.PageableHelp;
import com.escola.client.controller.help.SortInput;
import com.escola.client.model.entity.Contrato;
import com.escola.client.model.mapper.ContratoMapper;
import com.escola.client.model.request.ContratoRequest;
import com.escola.client.model.response.ContratoResponse;
import com.escola.client.service.ContratoService;
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
public class ContratoController {

    public static final String SUCESSO = "Sucesso";
    ContratoMapper contratoMapper;
    ContratoService contratoService;
    PageableHelp pageableHelp;

    @MutationMapping
    @PreAuthorize("isAuthenticated()")
    public ContratoResponse saveContrato(@Argument ContratoRequest request) {
        var entity = contratoService.save(request);
        return contratoMapper.toResponse(entity);
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public Page<ContratoResponse> fetchAllContratos(
            @Argument String filtro,
            @Argument Optional<Integer> page, // Optional to handle default values from schema
            @Argument Optional<Integer> size, // Optional to handle default values from schema
            @Argument Optional<List<SortInput>> sort // Optional for sorting
    ) {
        Page<Contrato> entities = contratoService.findByFiltro(filtro, pageableHelp.getPageable(page, size, sort)).orElse(Page.empty());
        return entities.map(contratoMapper::toResponse);
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public Optional<ContratoResponse> fetchByIdContrato(@Argument Long id) {
        return contratoService.findById(id).map(contratoMapper::toResponse);
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public String apagarContrato(@Argument Integer id) {
        contratoService.deleteById(id);
        return SUCESSO;
    }

}
