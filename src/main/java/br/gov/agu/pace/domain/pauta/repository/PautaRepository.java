package br.gov.agu.pace.domain.pauta.repository;

import br.gov.agu.pace.domain.enums.Turno;
import br.gov.agu.pace.domain.orgaoJulgador.OrgaoJulgadorEntity;
import br.gov.agu.pace.domain.pauta.entity.PautaEntity;
import br.gov.agu.pace.domain.sala.SalaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface PautaRepository extends JpaRepository<PautaEntity, Long> {

    Optional<PautaEntity> findByDataAndTurnoAndSalaAndOrgaoJulgador(
            LocalDate data,
            Turno turno,
            SalaEntity sala,
            OrgaoJulgadorEntity orgaoJulgador
    );
}
