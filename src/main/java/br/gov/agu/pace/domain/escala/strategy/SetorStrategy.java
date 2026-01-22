package br.gov.agu.pace.domain.escala.strategy;

import br.gov.agu.pace.domain.audiencia.entity.AudienciaEntity;
import br.gov.agu.pace.domain.user.UserEntity;

public interface SetorStrategy {

    Long getSetorId(UserEntity user, AudienciaEntity audiencia);
}
