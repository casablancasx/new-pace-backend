package br.gov.agu.pace.integrations.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetorDTO {

    private Long id;
    private String nome;
    private UnidadeDTO unidade;
}
