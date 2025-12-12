package br.gov.agu.pace.domain.planilha.entity;

import br.gov.agu.pace.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "tb_planilhas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanilhaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "planilha_id")
    private Long planilhaId;

    @Column(name = "nome_arquivo", nullable = false)
    private String nomeArquivo;

    @Column(name = "data_upload", nullable = false)
    private LocalDateTime dataUpload = LocalDateTime.now(
            ZoneId.systemDefault()
    );

    @Column(name = "hash", nullable = false, unique = true)
    private String hash;

    @ManyToOne
    @JoinColumn(name = "upload_pelo_id", nullable = false)
    private UserEntity usuario;

    private boolean processamentoConcluido = false;

    private int audiencias;

    private int pautas;
}
