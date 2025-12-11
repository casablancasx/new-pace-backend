package br.gov.agu.pace.domain.audiencia.entity;

import br.gov.agu.pace.domain.assunto.AssuntoEntity;
import br.gov.agu.pace.domain.enums.StatusCadastroTarefa;
import br.gov.agu.pace.domain.enums.TipoContestacao;
import br.gov.agu.pace.domain.pauta.entity.PautaEntity;
import br.gov.agu.pace.domain.sala.SalaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private String nomeParte;

    private String horario;

    private boolean isPrioritario;

    @Enumerated(EnumType.STRING)
    private TipoContestacao tipoContestacao;

    private String analise;

    @Enumerated(EnumType.STRING)
    private StatusCadastroTarefa statusCadastroTarefaAvaliador;

    @Enumerated(EnumType.STRING)
    private StatusCadastroTarefa statusCadastroTarefaPautista;

    @ManyToOne
    @JoinColumn(name = "assunto_id")
    private AssuntoEntity assunto;


    @ManyToOne
    @JoinColumn(name = "pauta_id")
    private PautaEntity pauta;

}
