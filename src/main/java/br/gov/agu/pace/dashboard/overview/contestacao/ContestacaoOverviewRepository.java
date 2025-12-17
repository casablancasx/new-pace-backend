package br.gov.agu.pace.dashboard.overview.contestacao;

import br.gov.agu.pace.domain.audiencia.entity.AudienciaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContestacaoOverviewRepository extends JpaRepository<AudienciaEntity, Long> {

    @Query(value = "SELECT COALESCE(a.tipo_contestacao, 'SEM_CONTESTACAO') AS tipoContestacao, COUNT(*) AS total "
            + "FROM tb_audiencias a "
            + "JOIN tb_pautas p ON a.pauta_id = p.pauta_id "
            + "WHERE EXTRACT(YEAR FROM p.data) = :ano "
            + "GROUP BY a.tipo_contestacao "
            + "ORDER BY a.tipo_contestacao",
            nativeQuery = true)
    List<ContestacaoCountProjection> countContestacoesPorTipoAno(Integer ano);

    @Query(value = "SELECT COALESCE(a.tipo_contestacao, 'SEM_CONTESTACAO') AS tipoContestacao, COUNT(*) AS total "
            + "FROM tb_audiencias a "
            + "JOIN tb_pautas p ON a.pauta_id = p.pauta_id "
            + "WHERE EXTRACT(YEAR FROM p.data) = :ano "
            + "AND EXTRACT(MONTH FROM p.data) = :mes "
            + "GROUP BY a.tipo_contestacao "
            + "ORDER BY a.tipo_contestacao",
            nativeQuery = true)
    List<ContestacaoCountProjection> countContestacoesPorTipoMes(Integer ano, Integer mes);

}

