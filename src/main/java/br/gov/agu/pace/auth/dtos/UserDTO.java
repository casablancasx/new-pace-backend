package br.gov.agu.pace.auth.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long sapiensId;
    private String nome;
    private String email;
    private Long setorId;
}
