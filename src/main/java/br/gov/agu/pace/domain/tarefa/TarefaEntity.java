package br.gov.agu.pace.domain.tarefa;

import br.gov.agu.pace.domain.audiencia.entity.AudienciaEntity;
import br.gov.agu.pace.domain.enums.StatusCadastroTarefa;
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
    private AudienciaEntity audiencia;

    @Enumerated(EnumType.STRING)
    private StatusCadastroTarefa status;

    private LocalDateTime criadoEm = LocalDateTime.now(ZoneId.systemDefault());

    @ManyToOne
    private UserEntity destinatario;

}
