package br.gov.agu.pace.domain.user.dto;

import br.gov.agu.pace.domain.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UsuarioResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String setor;
    private String unidade;
    private UserRole role;
    private boolean isContaAtiva;
}
