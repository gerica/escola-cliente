package com.escola.client.model.mapper;

import com.escola.client.model.entity.Cliente;
import com.escola.client.model.request.ClienteRequest;
import com.escola.client.model.response.ClienteResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    @Named("formatCPF")
    static String formatCpf(String cpf) {
        // Verifica se o CPF é nulo ou não tem 11 dígitos
        if (cpf == null || cpf.length() != 11) {
            return cpf; // Retorna o valor original se inválido
        }
        // Aplica a máscara XXX.XXX.XXX-XX
        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    ClienteResponse toOutput(Cliente entity);

    List<ClienteResponse> toOutputList(List<Cliente> empresas);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "docCPF", source = "docCPF", qualifiedByName = "formatCPF")
    Cliente toEntity(ClienteRequest request);

    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "endereco.enderecoCidade", source = "endereco.enderecoCidade", qualifiedByName = "mapCidadeToInteger")
    Cliente updateEntity(ClienteRequest source, @MappingTarget Cliente target);
}
