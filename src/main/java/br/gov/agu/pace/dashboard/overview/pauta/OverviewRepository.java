package br.gov.agu.pace.dashboard.overview.pauta;

import br.gov.agu.pace.domain.pauta.entity.PautaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OverviewRepository extends JpaRepository<PautaEntity, Long> {



    @Query(value = "SELECT EXTRACT(MONTH FROM p.data) AS mes, COUNT(*) AS total "
            + "FROM tb_pautas p "
            + "WHERE EXTRACT(YEAR FROM p.data) = :ano "
            + "GROUP BY mes "
            + "ORDER BY mes",
            nativeQuery = true)
    List<MonthlyCountProjection> countPautasPorMes(Integer ano);

    @Query(value = "SELECT EXTRACT(MONTH FROM p.data) AS mes, COUNT(a.audiencia_id) AS total "
            + "FROM tb_pautas p "
            + "LEFT JOIN tb_audiencias a ON a.pauta_id = p.pauta_id "
            + "WHERE EXTRACT(YEAR FROM p.data) = :ano "
            + "GROUP BY mes "
            + "ORDER BY mes",
            nativeQuery = true)
    List<MonthlyCountProjection> countAudienciasPorMes(Integer ano);

    @Query(value = "SELECT EXTRACT(DAY FROM p.data) AS dia, COUNT(*) AS total "
            + "FROM tb_pautas p "
            + "WHERE EXTRACT(YEAR FROM p.data) = :ano "
            + "AND EXTRACT(MONTH FROM p.data) = :mes "
            + "GROUP BY dia "
            + "ORDER BY dia",
            nativeQuery = true)
    List<DailyCountProjection> countPautasPorDia(Integer ano, Integer mes);

    @Query(value = "SELECT EXTRACT(DAY FROM p.data) AS dia, COUNT(a.audiencia_id) AS total "
            + "FROM tb_pautas p "
            + "LEFT JOIN tb_audiencias a ON a.pauta_id = p.pauta_id "
            + "WHERE EXTRACT(YEAR FROM p.data) = :ano "
            + "AND EXTRACT(MONTH FROM p.data) = :mes "
            + "GROUP BY dia "
            + "ORDER BY dia",
            nativeQuery = true)
    List<DailyCountProjection> countAudienciasPorDia(Integer ano, Integer mes);

}
