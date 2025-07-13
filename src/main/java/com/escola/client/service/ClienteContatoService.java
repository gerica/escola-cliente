package com.escola.client.service;


import com.escola.client.model.entity.ClienteContato;
import com.escola.client.model.request.ClienteContatoRequest;

import java.util.List;
import java.util.Optional;

public interface ClienteContatoService {

    ClienteContato save(ClienteContatoRequest request);

    Optional<Boolean> apagar(Integer id);

    Optional<ClienteContato> findById(Integer id);

    Optional<List<ClienteContato>> findAllByClienteId(Integer id);


}
