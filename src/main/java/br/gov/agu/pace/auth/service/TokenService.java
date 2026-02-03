package br.gov.agu.pace.auth.service;

import br.gov.agu.pace.auth.dtos.UserFromTokenDTO;
import br.gov.agu.pace.integrations.client.SapiensClient;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final SapiensClient sapiensClient;


    public UserFromTokenDTO getUserFromToken(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return new UserFromTokenDTO(
                jwt.getClaim("id").asLong(),
                jwt.getClaim("nome").asString(),
                jwt.getClaim("email").asString(),
                getSetorIdFromToken(jwt.getClaim("roles").asList(String.class)),
                token
        );
    }

    private Long getSetorIdFromToken(List<String> roles){

        return roles
                .stream()
                .filter(role -> role.startsWith("ACL_SETOR_"))
                .findFirst()
                .map(setorId -> Long.parseLong(setorId.replace("ACL_SETOR_", "")))
                .get();
    }

    public Long getSapiensIdFromToken(String token){

        if (token.startsWith("Bearer ")){
            token = token.replace("Bearer ", "");
        }

        return getUserFromToken(token).getSapiensId();
    }

    public String renovarTokenSeExpirado(String token) {
        DecodedJWT jwt = JWT.decode(token);

        long expMillis = jwt.getExpiresAt().getTime();
        long agora = System.currentTimeMillis();

        // 10 minutos antes (15 * 60 * 1000)
        long janelaRenovacao = 15 * 60 * 1000;

        // Se (Expiração - 10min) for menor ou igual ao tempo agora, renova.
        if (expMillis - janelaRenovacao <= agora) {
            System.out.println("RENOVANDO TOKEN");
            return sapiensClient.refreshToken(token);
        }

        return token;
    }

}
