package br.gov.agu.pace.auth.service;

import br.gov.agu.pace.client.SapiensClient;
import br.gov.agu.pace.auth.dtos.LoginRequestDTO;
import br.gov.agu.pace.auth.dtos.LoginResponseDTO;
import br.gov.agu.pace.auth.dtos.LoginSapiensApiResponse;
import br.gov.agu.pace.core.domain.user.UserEntity;
import br.gov.agu.pace.core.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final SapiensClient sapiensClient;
    private final UserService userService;

    public LoginResponseDTO login(LoginRequestDTO data){
        LoginSapiensApiResponse loginResponseSapiensAPI = sapiensClient.getTokenSuperSapiens(data);
        UserEntity user = userService.buscarOuCriarUsuario(loginResponseSapiensAPI.getToken());
        return new LoginResponseDTO(
                loginResponseSapiensAPI.getExp(),
                loginResponseSapiensAPI.isPasswordExpired(),
                loginResponseSapiensAPI.getTimestamp(),
                loginResponseSapiensAPI.getToken(),
                loginResponseSapiensAPI.getVersion(),
                user.getRole()
        );
    }
}
