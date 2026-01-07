package br.gov.agu.pace.escala;

import br.gov.agu.pace.domain.enums.TipoContestacao;
import br.gov.agu.pace.domain.enums.TipoEscala;
import br.gov.agu.pace.domain.pauta.entity.PautaEntity;
import br.gov.agu.pace.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;

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

    private LocalDateTime criadoEm = LocalDateTime.now(ZoneId.systemDefault());

    @ManyToOne
    @JoinColumn(name = "criador_id")
    private UserEntity criador;

    @ManyToOne(optional = false)
    private PautaEntity pauta;

    @ManyToOne
    private UserEntity usuario;

    @Enumerated(EnumType.STRING)
    private TipoEscala tipo;

    @Enumerated(EnumType.STRING)
    private StatusEscala status = StatusEscala.ESCALADA;

}
