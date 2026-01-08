package br.gov.agu.pace.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PautistaResponseDTO {

    private Long id;

    private String nome;

    private String email;

    private String setor;

    private String unidade;

    private Long quantidadeAudiencias;

    private Long quantidadePautas;

    private boolean disponivel;
}
