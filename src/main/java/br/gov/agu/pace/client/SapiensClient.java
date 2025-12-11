package br.gov.agu.pace.client;

import br.gov.agu.pace.auth.dtos.LoginRequestDTO;
import br.gov.agu.pace.auth.dtos.LoginSapiensApiResponse;
import br.gov.agu.pace.auth.dtos.SetorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;

@Component
@RequiredArgsConstructor
public class SapiensClient {

    private final RestClient restClient;


    public LoginSapiensApiResponse getTokenSuperSapiens(LoginRequestDTO data){
        try{
            return restClient
                    .post()
                    .uri("/auth/ldap_get_token")
                    .body(data)
                    .retrieve()
                    .body(LoginSapiensApiResponse.class);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public String refreshToken(String tokenAntigo){
        try{
            return restClient
                    .get()
                    .uri("/auth/refresh_token")
                    .header("Authorization", "Bearer " + tokenAntigo)
                    .retrieve()
                    .body(JsonNode.class)
                    .get("token").asString();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public SetorDTO getInformacoesSetorPorId(Long setorId, String token){
        try{
            var apiResponse = restClient
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/v1/administrativo/setor/{id}")
                            .queryParam("populate", "[\"unidade\"]")
                            .build(setorId)
                    )
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .body(JsonNode.class);

            return new SetorDTO(
                    apiResponse.get("id").asLong(),
                    apiResponse.get("nome").asString(),
                    apiResponse.get("unidade").get("id").asLong(),
                    apiResponse.get("nome").asString()
            );

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
