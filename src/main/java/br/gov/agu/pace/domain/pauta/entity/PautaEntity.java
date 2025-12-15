package br.gov.agu.pace.domain.pauta.entity;

import br.gov.agu.pace.domain.audiencia.entity.AudienciaEntity;
import br.gov.agu.pace.domain.enums.StatusEscalaPauta;
import br.gov.agu.pace.domain.enums.Turno;
import br.gov.agu.pace.domain.orgaoJulgador.OrgaoJulgadorEntity;
import br.gov.agu.pace.domain.sala.SalaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static br.gov.agu.pace.domain.enums.StatusCadastroTarefa.ERRO;
import static br.gov.agu.pace.domain.enums.StatusCadastroTarefa.SUCESSO;

@Entity
@Table(name = "tb_pautas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PautaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pauta_id")
    private Long pautaId;

    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "sala_id")
    private SalaEntity sala;

    @ManyToOne
    @JoinColumn(name = "orgao_julgador_id")
    private OrgaoJulgadorEntity orgaoJulgador;

    @Enumerated(EnumType.STRING)
    private Turno turno;

    @Column(name = "status_escala_avaliador")
    @Enumerated(EnumType.STRING)
    private StatusEscalaPauta statusEscalaAvaliador;

    @Column(name = "status_escala_pautista")
    @Enumerated(EnumType.STRING)
    private StatusEscalaPauta statusEscalaPautista;

    @OneToMany(mappedBy = "pauta")
    private List<AudienciaEntity> audiencias;

    //Flag para identificar se houve adicao de uma nova audiencia em uma pauta existente
    private boolean possuiNovaAudiencia;

    private LocalDateTime criadoEm = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));



    public Long getTotalAudienciasCadastradasComSucesso(){
        return audiencias.stream()
                .filter(a -> SUCESSO.equals(a.getStatusCadastroTarefaAvaliador()))
                .count();
    }

    public Long getTotalAudienciasCadastradasComErro(){
        return audiencias.stream()
                .filter(a -> ERRO.equals(a.getStatusCadastroTarefaAvaliador()))
                .count();
    }

}
