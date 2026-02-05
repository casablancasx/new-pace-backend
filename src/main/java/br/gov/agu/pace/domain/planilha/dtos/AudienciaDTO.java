package br.gov.agu.pace.domain.planilha.dtos;

import br.gov.agu.pace.domain.enums.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AudienciaDTO {

    private Long audienciaId;

    private String numeroProcesso;

    private LocalDate data;

    private String hora;

    private Turno turno;

    private String sala;

    private String orgaoJulgador;

    private String poloAtivo;

    private String poloPassivo;

    private String assunto;

    List<String> advogados;

    private Uf uf;

    private TipoContestacao tipoContestacao;

    private Long processoId;

    private ClasseJudicial classeJudicial;

    private Subnucleo subnucleo;

}
