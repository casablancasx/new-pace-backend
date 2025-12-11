package br.gov.agu.pace.auth.dtos;

import br.gov.agu.pace.domain.enums.UserRole;
import br.gov.agu.pace.integrations.dtos.LoginSapiensApiResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LoginResponseDTO extends LoginSapiensApiResponse {
    private UserRole role;

    public LoginResponseDTO(Long exp, boolean passwordExpired, Long timestamp, String token, String version, UserRole role) {
        super(exp, passwordExpired, timestamp, token, version);
        this.role = role;
    }

}
