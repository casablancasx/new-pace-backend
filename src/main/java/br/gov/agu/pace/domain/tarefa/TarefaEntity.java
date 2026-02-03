package br.gov.agu.pace.domain.tarefa;

import br.gov.agu.pace.domain.escala.EscalaEntity;
import br.gov.agu.pace.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "tb_tarefas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TarefaEntity {

    @Id
    private Long tarefaId;

    @OneToOne
    @JoinColumn(name = "escala_id", unique = true)
    private EscalaEntity escala;

    @Column(name = "setor_destino_id")
    private long setorDestino;


    private LocalDateTime criadoEm = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));


    @ManyToOne
    private UserEntity destinatario;

}
