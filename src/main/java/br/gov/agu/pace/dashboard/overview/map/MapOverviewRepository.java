package br.gov.agu.pace.dashboard.overview.map;

import br.gov.agu.pace.domain.pauta.entity.PautaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapOverviewRepository extends JpaRepository<PautaEntity, Long> {

    @Query(value = "SELECT u.sigla AS uf, "
            + "COUNT(DISTINCT p.pauta_id) AS pautas, "
            + "COUNT(a.audiencia_id) AS audiencias "
            + "FROM tb_pautas p "
            + "JOIN tb_orgaos_julgadores oj ON p.orgao_julgador_id = oj.orgao_julgador_id "
            + "JOIN tb_ufs u ON oj.uf_id = u.uf_id "
            + "LEFT JOIN tb_audiencias a ON a.pauta_id = p.pauta_id "
            + "WHERE EXTRACT(YEAR FROM p.data) = :ano "
            + "GROUP BY u.sigla "
            + "ORDER BY u.sigla",
            nativeQuery = true)
    List<MapCountProjection> countPautasEAudienciasPorUfAno(Integer ano);

    @Query(value = "SELECT u.sigla AS uf, "
            + "COUNT(DISTINCT p.pauta_id) AS pautas, "
            + "COUNT(a.audiencia_id) AS audiencias "
            + "FROM tb_pautas p "
            + "JOIN tb_orgaos_julgadores oj ON p.orgao_julgador_id = oj.orgao_julgador_id "
            + "JOIN tb_ufs u ON oj.uf_id = u.uf_id "
            + "LEFT JOIN tb_audiencias a ON a.pauta_id = p.pauta_id "
            + "WHERE EXTRACT(YEAR FROM p.data) = :ano "
            + "AND EXTRACT(MONTH FROM p.data) = :mes "
            + "GROUP BY u.sigla "
            + "ORDER BY u.sigla",
            nativeQuery = true)
    List<MapCountProjection> countPautasEAudienciasPorUfMes(Integer ano, Integer mes);
}

