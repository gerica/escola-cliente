package com.escola.client.model.mapper;

import com.escola.client.model.entity.ClienteContato;
import com.escola.client.model.request.ClienteContatoRequest;
import com.escola.client.model.response.ClienteContatoResponse;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface ClienteContatoMapper {

    ClienteContatoResponse toResponse(ClienteContato entity);

    List<ClienteContatoResponse> toResponseList(List<ClienteContato> empresas);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "dataCadastro", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    ClienteContato toEntity(ClienteContatoRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "dataCadastro", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    ClienteContato updateEntity(ClienteContatoRequest source, @MappingTarget ClienteContato target);
}
