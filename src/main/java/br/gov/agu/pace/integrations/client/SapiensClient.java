package br.gov.agu.pace.integrations.client;

import br.gov.agu.pace.auth.dtos.LoginRequestDTO;
import br.gov.agu.pace.integrations.dtos.LoginSapiensApiResponse;
import br.gov.agu.pace.integrations.dtos.SetorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;

@Component
@RequiredArgsConstructor
public class SapiensClient {

    private final RestClient restClient;


    public LoginSapiensApiResponse getTokenSuperSapiens(LoginRequestDTO data) {
        try {
            return restClient
                    .post()
                    .uri("/auth/ldap_get_token")
                    .body(data)
                    .retrieve()
                    .body(LoginSapiensApiResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String refreshToken(String tokenAntigo) {
        try {
            return restClient
                    .get()
                    .uri("/auth/refresh_token")
                    .header("Authorization", "Bearer " + tokenAntigo)
                    .retrieve()
                    .body(JsonNode.class)
                    .get("token").asString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public SetorDTO getInformacoesSetorPorId(Long setorId, String token) {
        try {
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

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Long getProcessoIdPorNumeroProcosso(String numeroProcesso, String token) {

        try{

            String numeroProcessoFormatado = numeroProcesso.replaceAll("[^0-9]", "");

            var apiResponse = restClient
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/v1/administrativo/processo")
                            .queryParam("where", "{\"andX\":[{\"vinculacoesProcessosJudiciaisProcessos.processoJudicial.numero\":\"like:" + numeroProcessoFormatado + "%\"}]}")
                            .queryParam("limit", "1")
                            .queryParam("offset", "0")
                            .build()
                    )
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .toEntity(JsonNode.class);


            return apiResponse.getBody().get("entities").get(0).get("id").asLong();

        }catch(Exception e){
            throw new RuntimeException(e);
        }



    }


    public Long getIdDocumentoContestaao(Long processoId, String token){
     try{
         var apiResponse = restClient
                 .get()
                 .uri(uriBuilder -> uriBuilder
                         .path("/v1/administrativo/juntada")
                         .queryParam("where", String.format("{\"volume.processo.id\":\"eq:%s\",\"documento.tipoDocumento.id\":\"eq:85\"}", processoId))
                         .queryParam("populate", "[\"documento\", \"documento.componentesDigitais\"]")
                         .queryParam("limit", "1")
                         .queryParam("offset", "0")
                         .queryParam("order", "{\"numeracaoSequencial\":\"ASC\"}")
                         .build()
                 )
                 .header("Authorization", "Bearer " + token)
                 .retrieve()
                 .toEntity(JsonNode.class);

         return apiResponse.getBody().get("entities").get(0).get("documento").get("componentesDigitais").get(0).get("id").asLong();
     }catch(Exception e){
          throw new RuntimeException(e);
      }
    }

    public String obterArquivoBase64PorIdDocumento(Long documentoId, String token){

        var apiResponse = restClient.get()
                .uri(String.format("/v1/administrativo/componente_digital/%s/download",documentoId))
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .toEntity(JsonNode.class);

        String conteudo = apiResponse.getBody().get("conteudo").asText();


        int index = conteudo.indexOf("base64,");
        return conteudo.substring(index + 7);
    }



}
