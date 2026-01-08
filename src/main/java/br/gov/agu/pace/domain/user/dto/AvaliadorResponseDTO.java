package br.gov.agu.pace.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AvaliadorResponseDTO {

    private Long id;

    private String nome;

    private String email;

    private String setor;

    private String unidade;

    private Long quantidadeAudiencias;

    private Long quantidadePautas;

    private Long quantidadeAudienciasAvaliadas;

    private boolean disponivel;

}
