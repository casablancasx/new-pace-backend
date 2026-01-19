package br.gov.agu.pace.escala.strategy;

import br.gov.agu.pace.domain.user.UserEntity;

public interface SetorStrategy {

    Long getSetorId(UserEntity user, Long especieTarefaId);
}
