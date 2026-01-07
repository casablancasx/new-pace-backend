package br.gov.agu.pace.escala;

import br.gov.agu.pace.domain.audiencia.entity.AudienciaEntity;
import br.gov.agu.pace.domain.audiencia.repository.AudienciaRepository;
import br.gov.agu.pace.domain.enums.StatusCadastroTarefa;
import br.gov.agu.pace.domain.enums.UserRole;

import br.gov.agu.pace.domain.user.UserEntity;
import br.gov.agu.pace.integrations.client.SapiensClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static br.gov.agu.pace.domain.enums.StatusCadastroTarefa.ERRO;
import static br.gov.agu.pace.domain.enums.StatusCadastroTarefa.SUCESSO;

@Service
@RequiredArgsConstructor
public class CadastrarTarefaService {


    private final SapiensClient sapiensClient;
    private final AudienciaRepository audienciaRepository;


    public AudienciaEntity cadastrarTarefa(Long setorOrigemId, Long especieTarefaId, UserEntity sapiensUser, AudienciaEntity audiencia, String token) {
        var statusCodeRequisicao = sapiensClient.cadastrarTarefaSapiens(sapiensUser,audiencia,setorOrigemId,especieTarefaId,token);
        var statusCadastroTarefa = statusCodeRequisicao.is2xxSuccessful() ? SUCESSO : ERRO;
        atualizarStatus(audiencia, sapiensUser, statusCadastroTarefa);
        return audienciaRepository.save(audiencia);
    }

    private void atualizarStatus(AudienciaEntity audienciaEntity, UserEntity sapiensUser, StatusCadastroTarefa statusCadastro) {

        if (sapiensUser.getRole().equals(UserRole.AVALIADOR)){
            audienciaEntity.setStatusCadastroTarefaAvaliador(statusCadastro);
        }

        if (sapiensUser.getRole().equals(UserRole.PAUTISTA)){
            audienciaEntity.setStatusCadastroTarefaPautista(statusCadastro);
        }
    }
}
