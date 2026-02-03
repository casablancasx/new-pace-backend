package br.gov.agu.pace.domain.audiencia.entity;

import br.gov.agu.pace.domain.advogado.AdvogadoEntity;
import br.gov.agu.pace.domain.assunto.AssuntoEntity;
import br.gov.agu.pace.domain.enums.*;
import br.gov.agu.pace.domain.escala.EscalaEntity;
import br.gov.agu.pace.domain.pauta.entity.PautaEntity;
import br.gov.agu.pace.domain.tarefa.TarefaEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tb_audiencias")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AudienciaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long audienciaId;

    private String numeroProcesso;

    @Column(name = "nome_parte", length = 500)
    private String nomeParte;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_audiencia_advogado",
            joinColumns = @JoinColumn(name = "audiencia_id"),
            inverseJoinColumns = @JoinColumn(name = "advogado_id")
    )
    private Set<AdvogadoEntity> advogados = new LinkedHashSet<>();

    private String horario;

    private boolean prioritaria;

    @Enumerated(EnumType.STRING)
    private TipoContestacao tipoContestacao;

    @Enumerated(EnumType.STRING)
    private RespostaAnaliseAvaliador analiseAvaliador;

    private String observacao;


    @ManyToOne
    @JoinColumn(name = "assunto_id")
    private AssuntoEntity assunto;

    @ManyToOne
    @JoinColumn(name = "pauta_id")
    private PautaEntity pauta;

    //Flag para identificar se houve adicao de uma nova audiencia em uma pauta existente
    private boolean novaAudiencia;

    private LocalDateTime criadoEm = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));

    @Column(name = "processo_id")
    private Long processoId;

    @Enumerated(EnumType.STRING)
    private ClasseJudicial classeJudicial;

    @Enumerated(EnumType.STRING)
    private Subnucleo subnucleo;


    @OneToMany(mappedBy = "audiencia", cascade = CascadeType.ALL)
    private List<EscalaEntity> escalas;

    public List<String> getAdvogados(){
        return advogados.stream().map(AdvogadoEntity::getNome).toList();
    }


}
