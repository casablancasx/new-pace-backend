package br.gov.agu.pace.domain.audiencia.dtos;

import br.gov.agu.pace.domain.enums.ClasseJudicial;
import br.gov.agu.pace.domain.enums.RespostaAnaliseAvaliador;
import br.gov.agu.pace.domain.enums.Subnucleo;
import br.gov.agu.pace.domain.enums.TipoContestacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnalisarAudienciaDTO {

    private Long audienciaId;

    private RespostaAnaliseAvaliador resposta;

    private TipoContestacao tipoContestacao;

    private ClasseJudicial classeJudicial;

    private Subnucleo subnucleo;

    private String observacao;
}
