package br.gov.agu.pace.domain.uf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UfResponseDTO {

    private int ufId;

    private String sigla;
}
