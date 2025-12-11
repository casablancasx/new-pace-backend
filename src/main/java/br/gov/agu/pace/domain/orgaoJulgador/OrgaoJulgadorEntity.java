package br.gov.agu.pace.domain.orgaoJulgador;

import br.gov.agu.pace.domain.uf.UfEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_orgaos_julgadores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrgaoJulgadorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long orgaoJulgadorId;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "uf_id")
    private UfEntity uf;
}
