package br.gov.agu.pace.escala.strategy;

import br.gov.agu.pace.domain.audiencia.entity.AudienciaEntity;
import br.gov.agu.pace.domain.enums.ClasseJudicial;
import br.gov.agu.pace.domain.enums.Subnucleo;
import br.gov.agu.pace.domain.user.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class PautistaSetorStrategy implements SetorStrategy{


    private static final Map<Subnucleo, Long> JEF_SETORES = Map.of(
            Subnucleo.ESEAS, 8057731L,
            Subnucleo.EBI, 8057767L,
            Subnucleo.ERU,8054827L
    );

    private static final Map<Subnucleo, Long> COMUM_SETORES = Map.of(
            Subnucleo.ESEAS, 8057730L,
            Subnucleo.EBI, 8057766L,
            Subnucleo.ERU,8059088L
    );

    @Override
    public Long getSetorId(UserEntity user, AudienciaEntity audiencia) {


        if (audiencia.getClasseJudicial().equals(ClasseJudicial.JEF)){
            return JEF_SETORES.get(audiencia.getSubnucleo());
        }else{
            return COMUM_SETORES.get(audiencia.getSubnucleo());
        }

    }
}
