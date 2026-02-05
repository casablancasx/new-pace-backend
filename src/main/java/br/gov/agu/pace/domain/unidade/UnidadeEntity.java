package br.gov.agu.pace.domain.unidade;

import br.gov.agu.pace.domain.setor.SetorEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_unidades")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UnidadeEntity {

    @Id
    private Long unidadeId;

    private String nome;

    private String sigla;

    @OneToMany(mappedBy = "unidade",fetch = FetchType.LAZY)
    private Set<SetorEntity> setores;

    public UnidadeEntity(Long unidadeId, String nome, String sigla) {
        this.unidadeId = unidadeId;
        this.sigla = sigla;
        this.nome = nome;
    }
}
