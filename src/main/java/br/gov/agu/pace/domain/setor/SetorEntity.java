package br.gov.agu.pace.domain.setor;

import br.gov.agu.pace.domain.user.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;

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

    @OneToMany(mappedBy = "setor")
    private HashSet<UserEntity> usuarios = new HashSet<>();

    public SetorEntity(Long setorId, String nome) {
        this.setorId = setorId;
        this.nome = nome;
    }
}
