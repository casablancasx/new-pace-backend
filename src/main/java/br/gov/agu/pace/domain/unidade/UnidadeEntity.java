package br.gov.agu.pace.domain.unidade;

import br.gov.agu.pace.domain.user.UserEntity;
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

    @OneToMany(mappedBy = "unidade")
    @JsonIgnore
    private Set<UserEntity> usuarios = new HashSet<>();

    public UnidadeEntity(Long unidadeId, String nome) {
        this.unidadeId = unidadeId;
        this.nome = nome;
    }
}
