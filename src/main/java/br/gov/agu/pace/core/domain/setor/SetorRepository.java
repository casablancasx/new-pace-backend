package br.gov.agu.pace.core.domain.setor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SetorRepository extends JpaRepository<SetorEntity, Long> {
}
