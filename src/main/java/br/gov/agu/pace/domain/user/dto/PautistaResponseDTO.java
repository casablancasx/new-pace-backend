package br.gov.agu.pace.domain.user.dto;

import br.gov.agu.pace.integrations.dtos.SetorDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PautistaResponseDTO {

    private Long id;

    private String nome;

    private String email;

    private List<SetorDTO> setores;

    private Long quantidadeAudiencias;

    private Long quantidadePautas;

    private boolean disponivel;
}
