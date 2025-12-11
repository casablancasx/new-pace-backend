package br.gov.agu.pace.core.domain.unidade;

import br.gov.agu.pace.core.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;

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

    @OneToMany(mappedBy = "unidade")
    private HashSet<UserEntity> usuarios = new HashSet<>();

    public UnidadeEntity(Long unidadeId, String nome) {
        this.unidadeId = unidadeId;
        this.nome = nome;
    }
}
