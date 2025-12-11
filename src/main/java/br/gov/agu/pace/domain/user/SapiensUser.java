package br.gov.agu.pace.domain.user;

import br.gov.agu.pace.domain.setor.SetorEntity;
import br.gov.agu.pace.domain.unidade.UnidadeEntity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class SapiensUser {

    @Id
    private Long sapiensId;

    private String email;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "setor_id")
    private SetorEntity setor;

    @ManyToOne
    @JoinColumn(name = "unidade_id")
    private UnidadeEntity unidade;

}
