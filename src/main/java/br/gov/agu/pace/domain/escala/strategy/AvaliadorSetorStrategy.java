package br.gov.agu.pace.domain.escala.strategy;

import br.gov.agu.pace.domain.audiencia.entity.AudienciaEntity;
import br.gov.agu.pace.domain.enums.Subnucleo;
import br.gov.agu.pace.domain.setor.SetorEntity;
import br.gov.agu.pace.domain.unidade.UnidadeEntity;
import br.gov.agu.pace.domain.user.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AvaliadorSetorStrategy implements SetorStrategy {


    @Override
    public Long getSetorId(UserEntity user, AudienciaEntity audiencia) {

        List<UnidadeEntity> unidades = user.getSetores().stream()
                .map(SetorEntity::getUnidade)
                .toList();


        Long setorId = null;

        for (UnidadeEntity unidade : unidades) {
            Subnucleo subnucleoDaUnidade = extrairSubnucleo(unidade.getSigla());
            assert subnucleoDaUnidade != null;
            if (subnucleoDaUnidade.equals(audiencia.getSubnucleo())) {
                setorId = unidade.getUnidadeId();
                break;
            }
        }

        return setorId;

    }

    private Subnucleo extrairSubnucleo(String sigla) {
        Pattern pattern = Pattern.compile("(EBI|ESEAS|ERU)\\d*", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(sigla);

        if (matcher.find()) {
            return Subnucleo.getSubnucleo(matcher.group(1));
        }
        return null;
    }

}
