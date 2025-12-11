package br.gov.agu.pace.domain.pauta.entity;

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

}
