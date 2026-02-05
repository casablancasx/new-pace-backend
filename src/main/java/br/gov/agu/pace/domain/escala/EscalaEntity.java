package br.gov.agu.pace.domain.escala;

import br.gov.agu.pace.domain.audiencia.entity.AudienciaEntity;
import br.gov.agu.pace.domain.enums.TipoEscala;
import br.gov.agu.pace.domain.pauta.entity.PautaEntity;
import br.gov.agu.pace.domain.tarefa.TarefaEntity;
import br.gov.agu.pace.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "tb_escalas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EscalaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "escala_id")
    private Long escalaId;

    private LocalDateTime criadoEm = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));

    @ManyToOne
    @JoinColumn(name = "criador_id")
    private UserEntity criador;

    @ManyToOne
    @JoinColumn(name = "audiencia_id")
    private AudienciaEntity audiencia;

    @ManyToOne
    private UserEntity usuario;

    @Enumerated(EnumType.STRING)
    private TipoEscala tipo;

    @OneToOne(mappedBy = "escala", optional = false)
    private TarefaEntity tarefa;

}
