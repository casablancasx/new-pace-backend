package br.gov.agu.pace.escala.strategy;

import br.gov.agu.pace.domain.user.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class PautistaSetorStrategy implements SetorStrategy{


    private static final Map<Long, Long> ESPECIE_PARA_SETOR = Map.of(
            1L, 1L,
            2L, 2L,
            3L,3L
    );

    @Override
    public Long getSetorId(UserEntity user, Long especieTarefaId) {

        Long codigoSetor = ESPECIE_PARA_SETOR.get(especieTarefaId);

        return user.getSetores().stream()
                .filter(setor -> setor.getSetorId().equals(codigoSetor))
                .findFirst()
                .get()
                .getSetorId();
    }
}
