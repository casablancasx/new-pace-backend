package br.gov.agu.pace.domain.user;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_metric")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserMetricEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "metric_id")
    private Long metricId;

    @OneToOne(optional = false)
    private UserEntity user;

    private Long quantidadeAudiencias = 0L;
    private Long quantidadePautas = 0L;
    private Long quantidadeAudienciasAvaliadas = 0L;

    private LocalDateTime atualizadoEm;



}
