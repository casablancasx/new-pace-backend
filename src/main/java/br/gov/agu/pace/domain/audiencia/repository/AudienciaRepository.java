package br.gov.agu.pace.domain.audiencia.repository;

import br.gov.agu.pace.domain.audiencia.entity.AudienciaEntity;
import br.gov.agu.pace.domain.enums.ClasseJudicial;
import br.gov.agu.pace.domain.enums.Subnucleo;
import br.gov.agu.pace.domain.enums.TipoContestacao;
import br.gov.agu.pace.domain.pauta.entity.PautaEntity;
import br.gov.agu.pace.relatorio.ContestacaoRelatorioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AudienciaRepository extends JpaRepository<AudienciaEntity, Long> {

    Optional<AudienciaEntity> findByNumeroProcesso(String numeroProcesso);

    Optional<AudienciaEntity> findByNumeroProcessoAndPauta(String numeroProcesso, PautaEntity pauta);

    List<AudienciaEntity> findByPauta(PautaEntity pauta);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM AudienciaEntity a WHERE a.numeroProcesso = :numeroProcesso AND a.processoId IS NOT NULL")
    boolean existsByNumeroProcessoAndProcessoIdIsNotNull(String numeroProcesso);

    @Query(value = "SELECT a FROM AudienciaEntity a WHERE (:numeroProcesso IS NULL OR a.numeroProcesso LIKE CONCAT('%', :numeroProcesso, '%')) AND (:orgaoJulgadorId IS NULL OR a.pauta.orgaoJulgador.orgaoJulgadorId = :orgaoJulgadorId)")
    Page<AudienciaEntity> listarAudiencias(String numeroProcesso, Long orgaoJulgadorId, Pageable pageable);

    @Query("""
        SELECT new br.gov.agu.pace.relatorio.ContestacaoRelatorioDTO(
            a.tipoContestacao,
            COUNT(a)
        )
        FROM AudienciaEntity a
        JOIN a.pauta p
        LEFT JOIN p.orgaoJulgador oj
        WHERE p.data BETWEEN :dataInicio AND :dataFim
        AND EXISTS (
            SELECT e FROM EscalaEntity e 
            WHERE e.audiencia = a
            AND (:userId IS NULL OR e.usuario.sapiensId = :userId)
        )
        AND (:orgaoJulgadorId IS NULL OR oj.orgaoJulgadorId = :orgaoJulgadorId)
        AND (:tipoContestacao IS NULL OR a.tipoContestacao = :tipoContestacao)
        AND (:subnucleo IS NULL OR a.subnucleo = :subnucleo)
        AND (:classeJudicial IS NULL OR a.classeJudicial = :classeJudicial)
        GROUP BY a.tipoContestacao
        """)
    List<ContestacaoRelatorioDTO> gerarRelatorioContestacao(
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim,
            @Param("userId") Long userId,
            @Param("orgaoJulgadorId") Long orgaoJulgadorId,
            @Param("tipoContestacao") TipoContestacao tipoContestacao,
            @Param("subnucleo") Subnucleo subnucleo,
            @Param("classeJudicial") ClasseJudicial classeJudicial
    );

    @Query("""
        SELECT COUNT(a)
        FROM AudienciaEntity a
        JOIN a.pauta p
        LEFT JOIN p.orgaoJulgador oj
        WHERE p.data BETWEEN :dataInicio AND :dataFim
        AND EXISTS (
            SELECT e FROM EscalaEntity e 
            WHERE e.audiencia = a
            AND (:userId IS NULL OR e.usuario.sapiensId = :userId)
        )
        AND (:orgaoJulgadorId IS NULL OR oj.orgaoJulgadorId = :orgaoJulgadorId)
        AND (:tipoContestacao IS NULL OR a.tipoContestacao = :tipoContestacao)
        AND (:subnucleo IS NULL OR a.subnucleo = :subnucleo)
        AND (:classeJudicial IS NULL OR a.classeJudicial = :classeJudicial)
        """)
    Long contarTotalAudiencias(
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim,
            @Param("userId") Long userId,
            @Param("orgaoJulgadorId") Long orgaoJulgadorId,
            @Param("tipoContestacao") TipoContestacao tipoContestacao,
            @Param("subnucleo") Subnucleo subnucleo,
            @Param("classeJudicial") ClasseJudicial classeJudicial
    );

    @Query("""
        SELECT COUNT(DISTINCT p)
        FROM AudienciaEntity a
        JOIN a.pauta p
        LEFT JOIN p.orgaoJulgador oj
        WHERE p.data BETWEEN :dataInicio AND :dataFim
        AND EXISTS (
            SELECT e FROM EscalaEntity e 
            WHERE e.audiencia = a
            AND (:userId IS NULL OR e.usuario.sapiensId = :userId)
        )
        AND (:orgaoJulgadorId IS NULL OR oj.orgaoJulgadorId = :orgaoJulgadorId)
        AND (:tipoContestacao IS NULL OR a.tipoContestacao = :tipoContestacao)
        AND (:subnucleo IS NULL OR a.subnucleo = :subnucleo)
        AND (:classeJudicial IS NULL OR a.classeJudicial = :classeJudicial)
        """)
    Long contarTotalPautas(
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim,
            @Param("userId") Long userId,
            @Param("orgaoJulgadorId") Long orgaoJulgadorId,
            @Param("tipoContestacao") TipoContestacao tipoContestacao,
            @Param("subnucleo") Subnucleo subnucleo,
            @Param("classeJudicial") ClasseJudicial classeJudicial
    );
}
