package br.gov.agu.pace.domain.escala.strategy;

import br.gov.agu.pace.domain.audiencia.entity.AudienciaEntity;
import br.gov.agu.pace.domain.enums.ClasseJudicial;
import br.gov.agu.pace.domain.enums.Subnucleo;
import br.gov.agu.pace.domain.setor.SetorEntity;
import br.gov.agu.pace.domain.unidade.UnidadeEntity;
import br.gov.agu.pace.domain.user.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class PautistaSetorStrategy implements SetorStrategy{




    @Override
    public Long getSetorId(UserEntity user, AudienciaEntity audiencia) {


        List<UnidadeEntity> unidades = user.getSetores().stream()
                .map(SetorEntity::getUnidade)
                .toList();

        Long setorId = null;

        for (UnidadeEntity unidade : unidades) {

            Subnucleo subnucleoDaUnidade = extrairSubnucleo(unidade.getSigla());
            ClasseJudicial classeDaUnidade = extrairClasse(unidade.getNome());
            assert subnucleoDaUnidade != null;

            if(audiencia.getSubnucleo().equals(subnucleoDaUnidade) && audiencia.getClasseJudicial().equals(classeDaUnidade)){
                setorId = unidade.getSetores().stream()
                        .filter(setor -> setor.getUnidade().getUnidadeId().equals(unidade.getUnidadeId()))
                        .findFirst()
                        .orElseThrow()
                        .getSetorId();
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

    private ClasseJudicial extrairClasse(String nome) {

        if (nome.contains("JEF") || nome.contains("JUIZADOS")){
            return ClasseJudicial.JEF;
        }else {
            return ClasseJudicial.COMUM;
        }
    }
}
