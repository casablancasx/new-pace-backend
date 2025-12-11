package br.gov.agu.pace.auth.service;

import br.gov.agu.pace.auth.dtos.LoginRequestDTO;
import br.gov.agu.pace.auth.dtos.LoginResponseDTO;
import br.gov.agu.pace.auth.dtos.UserFromTokenDTO;
import br.gov.agu.pace.integrations.client.SapiensClient;
import br.gov.agu.pace.integrations.dtos.LoginSapiensApiResponse;
import br.gov.agu.pace.domain.user.UserEntity;
import br.gov.agu.pace.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final SapiensClient sapiensClient;
    private final UserService userService;
    private final TokenService tokenService;

    public LoginResponseDTO login(LoginRequestDTO data){
        LoginSapiensApiResponse loginResponseSapiensAPI = sapiensClient.getTokenSuperSapiens(data);
        UserFromTokenDTO userFromToken = tokenService.getUserFromToken(loginResponseSapiensAPI.getToken());

        //Buscar um usuario existente ou criar um novo com base nos dados do token
        UserEntity user = userService.buscarOuCriarUsuario(userFromToken);
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
