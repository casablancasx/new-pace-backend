package br.gov.agu.pace.auth.service;

import br.gov.agu.pace.auth.dtos.UserDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenService {


    public UserDTO getUserFromToken(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return new UserDTO(
                jwt.getClaim("id").asLong(),
                jwt.getClaim("nome").asString(),
                jwt.getClaim("email").asString(),
                getSetorIdFromToken(jwt.getClaim("roles").asList(String.class))
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

}
