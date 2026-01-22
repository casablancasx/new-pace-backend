package br.gov.agu.pace.integrations.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnidadeDTO {
    private Long id;
    private String nome;
    private String sigla;
}
