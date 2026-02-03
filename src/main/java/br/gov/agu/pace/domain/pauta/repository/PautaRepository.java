package br.gov.agu.pace.domain.pauta.repository;

import br.gov.agu.pace.domain.enums.TipoContestacao;
import br.gov.agu.pace.domain.enums.Turno;
import br.gov.agu.pace.domain.enums.Uf;
import br.gov.agu.pace.domain.orgaoJulgador.OrgaoJulgadorEntity;
import br.gov.agu.pace.domain.pauta.entity.PautaEntity;
import br.gov.agu.pace.domain.sala.SalaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PautaRepository extends JpaRepository<PautaEntity, Long> {

    Optional<PautaEntity> findByDataAndTurnoAndSalaAndOrgaoJulgador(
            LocalDate data,
            Turno turno,
            SalaEntity sala,
            OrgaoJulgadorEntity orgaoJulgador
    );


    @Query("SELECT DISTINCT p FROM PautaEntity p " +
            "LEFT JOIN FETCH p.audiencias a " +
            "WHERE p.data BETWEEN :dataInicio AND :dataFim " +
            "AND (:ufs IS NULL OR p.orgaoJulgador.uf.sigla IN :ufs) " +
            "AND (:orgaoJulgadorIds IS NULL OR p.orgaoJulgador.orgaoJulgadorId IN :orgaoJulgadorIds) " +
            "AND (:tiposContestacao IS NULL OR a.tipoContestacao IN :tiposContestacao) " +
            "AND (p.isEscaladaAvaliador IS FALSE )")
    Set<PautaEntity> buscarPautasSemAvaliadoresEscalados(
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim,
            @Param("ufs") List<Uf> ufs,
            @Param("orgaoJulgadorIds") List<Long> orgaoJulgadorIds,
            @Param("tiposContestacao") List<TipoContestacao> tiposContestacao);


    @Query("SELECT DISTINCT p FROM PautaEntity p " +
            "LEFT JOIN FETCH p.audiencias a " +
            "WHERE p.data BETWEEN :dataInicio AND :dataFim " +
            "AND (:ufs IS NULL OR p.orgaoJulgador.uf.sigla IN :ufs) " +
            "AND (:orgaoJulgadorIds IS NULL OR p.orgaoJulgador.orgaoJulgadorId IN :orgaoJulgadorIds) " +
            "AND (:tiposContestacao IS NULL OR a.tipoContestacao IN :tiposContestacao) " +
            "AND a.analiseAvaliador = br.gov.agu.pace.domain.enums.RespostaAnaliseAvaliador.COMPARECER")
    Set<PautaEntity> buscarTodasPautasSemPautistasEscalados(
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim,
            @Param("ufs") List<Uf> ufs,
            @Param("orgaoJulgadorIds") List<Long> orgaoJulgadorIds,
            @Param("tiposContestacao") List<TipoContestacao> tiposContestacao);

    @Query("SELECT p FROM PautaEntity p " +
            "LEFT JOIN p.escala e " +
            "LEFT JOIN e.usuario u " +
            "WHERE (:orgaoJulgadorId IS NULL OR p.orgaoJulgador.orgaoJulgadorId = :orgaoJulgadorId) " +
            "AND (:uf IS NULL OR p.orgaoJulgador.uf.sigla = :uf) " +
            "AND (:userId IS NULL OR u.sapiensId = :userId)")
    Page<PautaEntity> listarPautas(@Param("orgaoJulgadorId") Long orgaoJulgadorId,
                                   @Param("userId") Long userId,
                                   @Param("uf") Uf uf,
                                   Pageable pageable);

    @Query("""
                SELECT DISTINCT p FROM PautaEntity p
                LEFT JOIN FETCH p.audiencias a
                LEFT JOIN FETCH a.advogados ad
                WHERE p.pautaId = :id
            """)
    PautaEntity buscarPorId(Long id);

    @Query("SELECT DISTINCT e.usuario.sapiensId FROM EscalaEntity e " +
            "WHERE e.audiencia.pauta.data = :data " +
            "AND e.tipo = br.gov.agu.pace.domain.enums.TipoEscala.PAUTISTA")
    List<Long> buscarPautistasEscaladosNaData(@Param("data") LocalDate data);
}

