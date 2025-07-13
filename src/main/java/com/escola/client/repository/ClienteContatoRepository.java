package com.escola.client.repository;

import com.escola.client.model.entity.ClienteContato;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClienteContatoRepository extends CrudRepository<ClienteContato, Integer> {

    @Query("SELECT e FROM ClienteContato e " +
            " WHERE e.cliente.id = :id")
    Optional<List<ClienteContato>> findAllByClienteId(@Param("id") Integer id);
}