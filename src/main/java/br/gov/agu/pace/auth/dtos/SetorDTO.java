package br.gov.agu.pace.auth.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetorDTO {

    private Long setorId;
    private String nomeSetor;
    private Long unidadeId;
    private String nomeUnidade;


}
