package com.escola.client.service.impl;

import com.escola.client.model.entity.Cliente;
import com.escola.client.model.entity.ClienteContato;
import com.escola.client.model.mapper.ClienteContatoMapper;
import com.escola.client.model.request.ClienteContatoRequest;
import com.escola.client.repository.ClienteContatoRepository;
import com.escola.client.service.ClienteContatoService;
import com.escola.client.service.ClienteService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ClienteContatoServiceImpl implements ClienteContatoService {

    ClienteContatoRepository repository;
    ClienteService clienteService;
    ClienteContatoMapper mapper;

    @Override
    public ClienteContato save(ClienteContatoRequest request) {
        ClienteContato entity;
        Optional<ClienteContato> optional = Optional.empty();
        if (request.id() != null) {
            optional = repository.findById(request.id());
        }

        if (optional.isPresent()) {
            entity = mapper.updateEntity(request, optional.get());
        } else {
            Optional<Cliente> optCli = clienteService.findById(request.idCliente());
            if (optCli.isEmpty()) {
                throw new RuntimeException("Cliente não encontrado");
            }
            entity = mapper.toEntity(request);
            entity.setCliente(optCli.get());
        }
        return repository.save(entity);
    }

    @Override
    public Optional<Boolean> apagar(Integer id) {
        repository.deleteById(id);
        return Optional.of(true);
    }

    @Override
    public Optional<ClienteContato> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Optional<List<ClienteContato>> findAllByClienteId(Integer id) {
        return repository.findAllByClienteId(id);
    }
}
