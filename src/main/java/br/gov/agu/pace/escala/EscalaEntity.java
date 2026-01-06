package br.gov.agu.pace.escala;

import br.gov.agu.pace.domain.avaliador.AvaliadorEntity;
import br.gov.agu.pace.domain.enums.TipoContestacao;
import br.gov.agu.pace.domain.pauta.entity.PautaEntity;
import br.gov.agu.pace.domain.pautista.PautistaEntity;
import br.gov.agu.pace.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
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

    @ElementCollection
    @CollectionTable(name = "tb_escala_tipos_contestacao_selecionados", joinColumns = @JoinColumn(name = "escala_id"))
    private Set<TipoContestacao> tiposSelecionados;

    @OneToMany(mappedBy = "escala")
    private Set<PautaEntity> pautas;

    @ManyToOne
    private PautistaEntity pautista;

    @ManyToOne
    private AvaliadorEntity avaliador;

}
