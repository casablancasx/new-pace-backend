package br.gov.agu.pace.domain.assunto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssuntoRepository extends JpaRepository<AssuntoEntity, Long> {

    Optional<AssuntoEntity> findByNome(String nome);
}
