package br.gov.agu.pace.domain.audiencia.repository;

import br.gov.agu.pace.domain.audiencia.entity.AudienciaEntity;
import br.gov.agu.pace.domain.pauta.entity.PautaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AudienciaRepository extends JpaRepository<AudienciaEntity, Long> {

    Optional<AudienciaEntity> findByNumeroProcesso(String numeroProcesso);

    Optional<AudienciaEntity> findByNumeroProcessoAndPauta(String numeroProcesso, PautaEntity pauta);

    List<AudienciaEntity> findByPauta(PautaEntity pauta);
}
