package br.gov.agu.pace.domain.setor;

import br.gov.agu.pace.domain.unidade.UnidadeEntity;
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
@Table(name = "tb_setores")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SetorEntity {

    @Id
    private Long setorId;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "unidade_id")
    private UnidadeEntity unidade;

    @OneToMany(mappedBy = "setor")
    @JsonIgnore
    private Set<UserEntity> usuarios = new HashSet<>();

    public SetorEntity(Long setorId, String nome) {
        this.setorId = setorId;
        this.nome = nome;
    }
}
