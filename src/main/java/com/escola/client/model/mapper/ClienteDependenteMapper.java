package com.escola.client.model.mapper;

import com.escola.client.model.entity.ClienteDependente;
import com.escola.client.model.request.ClienteDependenteRequest;
import com.escola.client.model.response.ClienteDependenteResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteDependenteMapper {

    @Mapping(target = "parentescoDescricao", source = "parentesco.descricao")
    ClienteDependenteResponse toOutput(ClienteDependente entity);

    List<ClienteDependenteResponse> toOutputList(List<ClienteDependente> empresas);

    @Mapping(target = "id", ignore = true)
    ClienteDependente toEntity(ClienteDependenteRequest request);

    @Mapping(target = "id", ignore = true)
    ClienteDependente updateEntity(ClienteDependenteRequest source, @MappingTarget ClienteDependente target);
}
