package com.escola.client.service.impl;

import com.escola.client.model.entity.User;
import com.escola.client.model.response.MunicipioResponse;
import com.escola.client.security.BaseException;
import com.escola.client.service.MunicipioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service(value = "municipioService")
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MunicipioServiceImpl implements MunicipioService {

//    private static final String KEY_MUNICIPIOS = AppConstant.NOME_APLICATIVO + ":municipios";

    @Value("classpath:cidades.json")
    Resource cidadeResource;

    @Value("classpath:temp.json")
    Resource tempResource;

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
//    @Cacheable(KEY_MUNICIPIOS)
    public List<MunicipioResponse> findAll() throws BaseException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
//       // Read the JSON file from the resources directory and map it to a list of Cidade objects
        return objectMapper.readValue(cidadeResource.getInputStream(), objectMapper.getTypeFactory().constructCollectionType(List.class, MunicipioResponse.class));
    }

    @Override
    public MunicipioResponse findByID(String codigo, User user) throws BaseException, IOException {
        List<MunicipioResponse> lista = findAll();
        Stream<MunicipioResponse> stream = lista.stream().filter(m -> m.getCodigo().equals(codigo));
        Optional<MunicipioResponse> first = stream.findFirst();
        return first.orElse(null);
    }

}
