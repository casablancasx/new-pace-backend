package br.gov.agu.pace.domain.pauta.entity;

import br.gov.agu.pace.domain.audiencia.entity.AudienciaEntity;
import br.gov.agu.pace.domain.enums.Turno;
import br.gov.agu.pace.domain.orgaoJulgador.OrgaoJulgadorEntity;
import br.gov.agu.pace.domain.sala.SalaEntity;
import br.gov.agu.pace.domain.escala.EscalaEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;

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

    private boolean isEscaladaAvaliador;

    private boolean isEscaladaPautista;


    @JsonIgnore
    @OneToMany(mappedBy = "pauta", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<AudienciaEntity> audiencias;


    private LocalDateTime criadoEm = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));


    public String getTurno(){
        return this.turno.getDescricao();
    }

    public boolean isPossuiNovaAudiencia(){
        return audiencias.stream().filter(a -> a.isNovaAudiencia()).findFirst().isPresent();
    }




}
