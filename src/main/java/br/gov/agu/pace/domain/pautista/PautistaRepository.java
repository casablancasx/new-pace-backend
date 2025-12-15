package br.gov.agu.pace.domain.pautista;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PautistaRepository extends JpaRepository<PautistaEntity, Long> {
}
