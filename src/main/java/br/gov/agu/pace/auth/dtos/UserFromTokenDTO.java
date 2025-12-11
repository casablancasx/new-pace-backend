package br.gov.agu.pace.auth.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFromTokenDTO {
    private Long sapiensId;
    private String nome;
    private String email;
    private Long setorId;
    private String token;
}
