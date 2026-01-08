package br.gov.agu.pace.domain.user;

import br.gov.agu.pace.domain.audiencia.entity.AudienciaEntity;
import br.gov.agu.pace.domain.enums.UserRole;
import br.gov.agu.pace.domain.setor.SetorEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.TimeZone;

@Entity
@Table(name = "tb_users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"setor", "metric"})
@EqualsAndHashCode(of = "sapiensId")
public class UserEntity{

    @Id
    @Column(name = "sapiens_id")
    private Long sapiensId;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "telefone", nullable = true)
    private String telefone;

    @ManyToOne
    @JoinColumn(name = "setor_id")
    private SetorEntity setor;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private LocalDateTime ultimoAcesso;

    private LocalDateTime dataCadastro = LocalDateTime.now(TimeZone.getDefault().toZoneId());

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserMetricEntity metric;

    private boolean disponivel = true;

    private boolean isContaAtiva = true;


    public void incrementarPautas(){
        this.metric.setQuantidadePautas(this.metric.getQuantidadePautas() + 1);
    }

    public void incrementarAudiencias(List<AudienciaEntity> audiencias){
        this.metric.setQuantidadeAudiencias(this.metric.getQuantidadeAudiencias() + audiencias.size());
    }

    public void incrementarQuantidadeAudienciasAnalisadas(){
        this.metric.setQuantidadeAudienciasAvaliadas(this.metric.getQuantidadeAudienciasAvaliadas() + 1);
    }

    public Long calcularCargaTrabalho() {
        final int PESO_PAUTA = 1;
        final int PESO_AUDIENCIA = 2;
        long quantidadePautas = metric.getQuantidadePautas();
        long quantidadeAudiencias = metric.getQuantidadeAudiencias();
        return (quantidadePautas * PESO_PAUTA) + (quantidadeAudiencias * PESO_AUDIENCIA);
    }
}
