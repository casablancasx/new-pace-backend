package br.gov.agu.pace.escala.strategy;

import br.gov.agu.pace.domain.user.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class AvaliadorSetorStrategy implements SetorStrategy{

    @Override
    public Long getSetorId(UserEntity user, Long especieTarefaId) {
        return user.getSetores().stream()
                .findFirst()
                .get()
                .getSetorId();
    }

}
