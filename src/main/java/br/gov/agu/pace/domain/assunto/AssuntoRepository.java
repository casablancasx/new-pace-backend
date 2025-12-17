package br.gov.agu.pace.domain.assunto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssuntoRepository extends JpaRepository<AssuntoEntity, Long> {

    Optional<AssuntoEntity> findByNome(String nome);

    @Query("SELECT a FROM AssuntoEntity a WHERE (:nome IS NULL OR a.nome LIKE CONCAT('%', :nome, '%'))")
    Page<AssuntoEntity> buscarAssuntos(String nome, Pageable pageable);
}
