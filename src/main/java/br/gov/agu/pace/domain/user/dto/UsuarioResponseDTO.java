package br.gov.agu.pace.domain.user.dto;

import br.gov.agu.pace.domain.enums.UserRole;
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
public class UsuarioResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private List<SetorDTO> sotores;
    private UserRole role;
    private boolean isContaAtiva;
}
