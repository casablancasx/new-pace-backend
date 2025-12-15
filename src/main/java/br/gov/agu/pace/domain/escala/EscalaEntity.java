package br.gov.agu.pace.domain.escala;

import br.gov.agu.pace.domain.avaliador.AvaliadorEntity;
import br.gov.agu.pace.domain.pauta.entity.PautaEntity;
import br.gov.agu.pace.domain.pautista.PautistaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_escalas", uniqueConstraints = @UniqueConstraint(columnNames = {"pauta_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EscalaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long escalaId;

    @ManyToOne
    @JoinColumn(name = "pauta_id")
    private PautaEntity pauta;

    @ManyToOne
    @JoinColumn(name = "pautista_id")
    private PautistaEntity pautista;

    @ManyToOne
    @JoinColumn(name = "avaliador_id")
    private AvaliadorEntity avaliador;

    private LocalDateTime criadoEm;

    @PrePersist
    protected void onCreate() {
        this.criadoEm = LocalDateTime.now();
    }
}
