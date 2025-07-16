package com.escola.client.repository;

import com.escola.client.model.entity.Contrato;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ContratoRepository extends CrudRepository<Contrato, Integer> {

    @Query("SELECT e FROM Contrato e " +
            " WHERE (:criteria IS NULL OR :criteria = '') OR " +
            " (LOWER(e.numeroContrato) LIKE LOWER(CONCAT('%', :criteria, '%')) ) OR " +
            " (LOWER(FUNCTION('TO_CHAR', e.dataInicio, 'DD/MM/YYYY')) LIKE LOWER(CONCAT('%', :criteria, '%'))) OR" +
            " (LOWER(FUNCTION('TO_CHAR', e.dataInicio, 'MM/YYYY')) LIKE LOWER(CONCAT('%', :criteria, '%'))) OR" +
            " (LOWER(FUNCTION('TO_CHAR', e.dataInicio, 'YYYY')) LIKE LOWER(CONCAT('%', :criteria, '%')))")
    Optional<Page<Contrato>> findByFiltro(@Param("criteria") String filtro, Pageable pageable);
}