package br.gov.agu.pace.domain.escala;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EscalaRepository extends JpaRepository<EscalaEntity, Long> {
}
