package br.gov.agu.pace.domain.escala;

import br.gov.agu.pace.domain.enums.ClasseJudicial;
import br.gov.agu.pace.domain.enums.Subnucleo;
import br.gov.agu.pace.domain.enums.TipoContestacao;
import br.gov.agu.pace.relatorio.EscalaRelatorioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface EscalaRepository extends JpaRepository<EscalaEntity, Long> {


    @Query("""
            SELECT new br.gov.agu.pace.relatorio.EscalaRelatorioDTO(
                e.escalaId,
                p.data,
                a.horario,
                a.numeroProcesso,
                u.nome,
                s.nome,
                oj.nome,
                a.classeJudicial,
                a.subnucleo,
                a.tipoContestacao,
                p.turno,
                a.analiseAvaliador,
                a.observacao
            )
            FROM EscalaEntity e
            JOIN e.audiencia a
            JOIN a.pauta p
            LEFT JOIN e.usuario u
            LEFT JOIN p.sala s
            LEFT JOIN p.orgaoJulgador oj
            WHERE p.data BETWEEN :dataInicio AND :dataFim
            AND (:userId IS NULL OR u.sapiensId = :userId)
            AND (:orgaoJulgadorId IS NULL OR oj.orgaoJulgadorId = :orgaoJulgadorId)
            AND (:tipoContestacao IS NULL OR a.tipoContestacao = :tipoContestacao)
            AND (:subnucleo IS NULL OR a.subnucleo = :subnucleo)
            AND (:classeJudicial IS NULL OR a.classeJudicial = :classeJudicial)
            """)
    Page<EscalaRelatorioDTO> gerarRelatorioEscala(
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim,
            @Param("userId") Long userId,
            @Param("orgaoJulgadorId") Long orgaoJulgadorId,
            @Param("tipoContestacao") TipoContestacao tipoContestacao,
            @Param("subnucleo") Subnucleo subnucleo,
            @Param("classeJudicial") ClasseJudicial classeJudicial,
            Pageable pageable
    );


}
