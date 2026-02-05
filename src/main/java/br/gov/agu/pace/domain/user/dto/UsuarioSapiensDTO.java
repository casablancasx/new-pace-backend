package br.gov.agu.pace.domain.user.dto;

import br.gov.agu.pace.integrations.dtos.SetorDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioSapiensDTO {

    private Long sapiensId;

    private String nome;

    private String email;

    private String telefone;

    private List<SetorDTO> setores;
}