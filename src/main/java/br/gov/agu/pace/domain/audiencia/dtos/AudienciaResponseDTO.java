package br.gov.agu.pace.domain.audiencia.dtos;

import br.gov.agu.pace.domain.enums.ClasseJudicial;
import br.gov.agu.pace.domain.enums.Subnucleo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AudienciaResponseDTO {

    private Long audienciaId;

    private String numeroProcesso;

    private String horario;

    private LocalDate data;

    private String nomeParte;

    private List<String> advogados;

    private String assunto;

    private boolean novaAudiencia;

    private String tipoContestacao;

    private String analiseAvaliador;

    private String observacao;

    private String pautista;

    private String avaliador;

    private Subnucleo subnucleo;

    private ClasseJudicial classeJudicial;

}
