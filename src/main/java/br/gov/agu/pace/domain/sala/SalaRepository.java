package br.gov.agu.pace.domain.sala;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalaRepository extends JpaRepository<SalaEntity, Long> {

    Optional<SalaEntity> findByNome(String nome);
}
