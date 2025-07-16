package com.escola.client.model.mapper;

import com.escola.client.model.entity.ClienteDependente;
import com.escola.client.model.request.ClienteDependenteRequest;
import com.escola.client.model.response.ClienteDependenteResponse;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface ClienteDependenteMapper {

    @Mapping(target = "parentescoDescricao", source = "parentesco.descricao")
    ClienteDependenteResponse toResponse(ClienteDependente entity);

    List<ClienteDependenteResponse> toResponseList(List<ClienteDependente> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "dataCadastro", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    ClienteDependente toEntity(ClienteDependenteRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "dataCadastro", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    ClienteDependente updateEntity(ClienteDependenteRequest source, @MappingTarget ClienteDependente target);
}
