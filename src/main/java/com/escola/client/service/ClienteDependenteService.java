package com.escola.client.service;

import com.escola.client.model.entity.ClienteDependente;
import com.escola.client.model.request.ClienteDependenteRequest;

import java.util.List;
import java.util.Optional;

public interface ClienteDependenteService {

    ClienteDependente save(ClienteDependenteRequest request);

    Optional<Boolean> apagar(Integer id);

    Optional<ClienteDependente> findById(Integer id);

    Optional<List<ClienteDependente>> findAllByClienteId(Integer id);


}
