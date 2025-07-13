package com.escola.client.repository;

import com.escola.client.model.entity.ClienteDependente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteDependenteRepository extends CrudRepository<ClienteDependente, Integer> {

    @Query("SELECT e FROM ClienteDependente e " +
            " WHERE e.cliente.id = :id")
    Optional<List<ClienteDependente>> findAllByClienteId(Integer id);
}