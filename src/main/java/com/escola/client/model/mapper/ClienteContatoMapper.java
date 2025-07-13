package com.escola.client.model.mapper;

import com.escola.client.model.entity.ClienteContato;
import com.escola.client.model.request.ClienteContatoRequest;
import com.escola.client.model.response.ClienteContatoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteContatoMapper {

    ClienteContatoResponse toOutput(ClienteContato entity);

    List<ClienteContatoResponse> toOutputList(List<ClienteContato> empresas);

    @Mapping(target = "id", ignore = true)
    ClienteContato toEntity(ClienteContatoRequest request);

    @Mapping(target = "id", ignore = true)
    ClienteContato updateEntity(ClienteContatoRequest source, @MappingTarget ClienteContato target);
}
