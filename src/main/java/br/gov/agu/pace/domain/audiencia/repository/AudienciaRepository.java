package br.gov.agu.pace.domain.audiencia.repository;

import br.gov.agu.pace.domain.audiencia.entity.AudienciaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AudienciaRepository extends JpaRepository<AudienciaEntity, Long> {

    Optional<AudienciaEntity> findByNumeroProcesso(String numeroProcesso);
}
