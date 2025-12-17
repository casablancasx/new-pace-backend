package br.gov.agu.pace.domain.sala;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalaRepository extends JpaRepository<SalaEntity, Long> {

    Optional<SalaEntity> findByNome(String nome);


    @Query("SELECT s FROM SalaEntity s WHERE (:nome IS NULL OR s.nome LIKE CONCAT('%', :nome, '%'))")
    Page<SalaEntity> buscarSalas(String nome, Pageable pageable);
}
