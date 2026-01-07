package br.gov.agu.pace.domain.user;

import br.gov.agu.pace.domain.enums.UserRole;
import br.gov.agu.pace.domain.setor.SetorEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.TimeZone;

@Entity
@Table(name = "tb_users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
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
    private SetorEntity setor;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private LocalDateTime ultimoAcesso;

    private LocalDateTime dataCadastro = LocalDateTime.now(TimeZone.getDefault().toZoneId());

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserMetricEntity metric;


    public Long calcularCargaTrabalho() {
        final int PESO_PAUTA = 1;
        final int PESO_AUDIENCIA = 2;
        long quantidadePautas = metric.getQuantidadePautas();
        long quantidadeAudiencias = metric.getQuantidadeAudiencias();
        return (quantidadePautas * PESO_PAUTA) + (quantidadeAudiencias * PESO_AUDIENCIA);
    }
}
