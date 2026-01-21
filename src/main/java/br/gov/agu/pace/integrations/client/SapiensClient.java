package br.gov.agu.pace.integrations.client;

import br.gov.agu.pace.auth.dtos.LoginRequestDTO;
import br.gov.agu.pace.domain.audiencia.entity.AudienciaEntity;
import br.gov.agu.pace.domain.enums.Subnucleo;
import br.gov.agu.pace.domain.user.UserEntity;
import br.gov.agu.pace.integrations.dtos.LoginSapiensApiResponse;
import br.gov.agu.pace.integrations.dtos.SetorDTO;
import io.swagger.v3.core.util.Json;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import tools.jackson.databind.BeanDescription;
import tools.jackson.databind.JsonNode;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


    //Retorna ID da tarefa criada no Sapiens ou null em caso de erro
    public Long cadastrarTarefaSapiens(UserEntity user, AudienciaEntity audiencia, Long setorOrigemId, Long especieTarefaId,Long setorDestino, String token) {

        var pauta = audiencia.getPauta();

        Map<String, Object> body = new HashMap<>();
        body.put("postIt", null);
        body.put("urgente", null);
        body.put("observacao", String.format("%s - %s - %s - %s",
                audiencia.getTipoContestacao().getDescricao() != null ? audiencia.getTipoContestacao() : "N/A",
                pauta.getData() != null ? pauta.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A",
                pauta.getOrgaoJulgador() != null ? pauta.getOrgaoJulgador().getNome() : "N/A",
                pauta.getTurno() != null ? pauta.getTurno() : "N/A"));
        body.put("localEvento", null);
        body.put("dataHoraInicioPrazo", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        body.put("dataHoraFinalPrazo", pauta.getData().atTime(20, 0, 0).format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME));        body.put("dataHoraLeitura", null);
        body.put("dataHoraDistribuicao", null);
        body.put("processo", audiencia.getProcessoId());
        body.put("especieTarefa", especieTarefaId); // Espécie Tarefa
        body.put("usuarioResponsavel", user.getSapiensId()); //Quem vai receber a tarefa
        body.put("setorOrigem", setorOrigemId);
        body.put("setorResponsavel", setorDestino); //Setor do usuário
        body.put("distribuicaoAutomatica", false);
        body.put("folder", null);
        body.put("prazoDias", ChronoUnit.DAYS.between(LocalDateTime.now(), pauta.getData().atTime(23, 59, 59)));
        body.put("isRelevante", null);
        body.put("locked", null);
        body.put("diasUteis", null);
        body.put("blocoProcessos", null);
        body.put("processos", null);
        body.put("blocoResponsaveis", null);
        body.put("grupoContato", null);
        body.put("usuarios", null);
        body.put("setores", null);

        try{
            return restClient.post()
                    .uri("/v1/administrativo/tarefa")
                    .body(body)
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .toEntity(JsonNode.class)
                    .getBody().get("id").asLong();
        }catch (Exception exception){
            return null;
        }

    }


    public Subnucleo getSubnucleoFromProcesso(Long processoId, String token) {

        String whereParam = String.format("where={\"processo.id\":\"eq:%s\",\"especieTarefa.generoTarefa.nome\":\"eq:JUDICIAL\"}",processoId);
        String limitParam = "limit=100";
        String offsetParam = "offset=0";
        String populateParam = "populate=[\"setorResponsavel\",\"setorResponsavel.unidade\"]";
        String orderParam = "order={\"dataHoraFinalPrazo\":\"desc\"}";


        URI uri = UriComponentsBuilder
                .fromUriString("https://supersapiensbackend.agu.gov.br/v1/administrativo/tarefa")
                .query(whereParam)
                .query(limitParam)
                .query(populateParam)
                .query(offsetParam)
                .query(orderParam)
                .build()
                .toUri();


        var apiResponse = restClient.get()
                .uri(uri)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .toEntity(JsonNode.class);

        JsonNode entities = apiResponse.getBody().get("entities");

        String sigla = null;
        for (JsonNode entity : entities) {
            JsonNode setor = entity.get("setorResponsavel");
            JsonNode unidade = setor.get("unidade");
            String siglaSetor = unidade.get("sigla").asString();
            sigla = extrairSubnucleo(siglaSetor);
            if (sigla != null) break;
        }
        
        return Subnucleo.getSubnucleo(sigla);
    }

    private String extrairSubnucleo(String nomeSetor) {
        Pattern pattern = Pattern.compile("(EBI|ESEAS|ERU)\\d*", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(nomeSetor);
        return matcher.find() ? matcher.group(1).toUpperCase() : null;
    }




}
