package br.gov.agu.pace.escala.strategy;

import br.gov.agu.pace.domain.audiencia.entity.AudienciaEntity;
import br.gov.agu.pace.domain.enums.Subnucleo;
import br.gov.agu.pace.domain.user.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AvaliadorSetorStrategy implements SetorStrategy{

    private static final Map<Subnucleo, Long> MAP_SETORES = Map.of(
            Subnucleo.ESEAS, 8057746L,
            Subnucleo.EBI, 8057765L,
            Subnucleo.ERU,8054825L
    );



    @Override
    public Long getSetorId(UserEntity user, AudienciaEntity audiencia) {
        return MAP_SETORES.get(audiencia.getSubnucleo());
    }

}
