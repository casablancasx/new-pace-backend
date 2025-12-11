package br.gov.agu.pace.integrations.client;

import br.gov.agu.pace.auth.dtos.LoginRequestDTO;
import br.gov.agu.pace.integrations.dtos.LoginSapiensApiResponse;
import br.gov.agu.pace.integrations.dtos.SetorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import tools.jackson.databind.JsonNode;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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
        String numeroLimpo = numeroProcesso.replaceAll("[^0-9]", "");

        // Monta a URL já com tudo codificado, sem passar pela lambda do UriBuilder
        String whereParam = "where={\"andX\":[{\"vinculacoesProcessosJudiciaisProcessos.processoJudicial.numero\":\"like:"+ numeroLimpo +"%\"}]}";
        String limitParam = "limit=1";
        String offsetParam = "offset=0";

        URI uri = UriComponentsBuilder.fromUriString("https://supersapiensbackend.agu.gov.br/v1/administrativo/processo")
                .query(whereParam)
                .query(limitParam)
                .query(offsetParam)
                .build()
                .toUri();


        var response = restClient.get()
                .uri(uri)                     // ← passa a string pronta, NÃO usa lambda
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .toEntity(JsonNode.class);

        return response.getBody().get("entities").get(0).get("id").asLong();
    }


    public Long getIdDocumentoContestaao(Long processoId, String token){
     try{

         String whereParam = String.format("where={\"volume.processo.id\":\"eq:%s\",\"documento.tipoDocumento.id\":\"eq:85\"}",processoId);
         String populateParam = "populate=[\"documento\", \"documento.componentesDigitais\"]";
         String limitParam = "limit=1";
         String offsetParam = "offset=0";
         String orderParam = "order={\"numeracaoSequencial\":\"ASC\"}";


         URI uri = UriComponentsBuilder.fromUriString("https://supersapiensbackend.agu.gov.br/v1/administrativo/juntada")
                 .query(whereParam)
                 .query(limitParam)
                 .query(populateParam)
                 .query(offsetParam)
                 .query(orderParam)
                 .build()
                 .toUri();




         var apiResponse = restClient
                 .get()
                 .uri(uri)
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
