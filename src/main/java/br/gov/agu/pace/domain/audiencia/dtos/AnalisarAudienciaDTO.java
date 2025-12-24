package br.gov.agu.pace.domain.audiencia.dtos;

import br.gov.agu.pace.domain.enums.RespostaAnaliseAvaliador;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnalisarAudienciaDTO {

    private Long audienciaId;

    private RespostaAnaliseAvaliador resposta;

    private String observacao;
}
