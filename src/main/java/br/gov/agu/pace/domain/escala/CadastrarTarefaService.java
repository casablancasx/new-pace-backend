package br.gov.agu.pace.domain.escala;

import br.gov.agu.pace.domain.audiencia.entity.AudienciaEntity;
import br.gov.agu.pace.domain.audiencia.repository.AudienciaRepository;
import br.gov.agu.pace.domain.tarefa.TarefaEntity;
import br.gov.agu.pace.domain.tarefa.TarefaRepository;
import br.gov.agu.pace.domain.user.UserEntity;
import br.gov.agu.pace.domain.escala.strategy.SetorStrategy;
import br.gov.agu.pace.domain.escala.strategy.SetorStrategyFactory;
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
    private final TarefaRepository tarefaRepository;
    private final SetorStrategyFactory setorStrategyFactory;


    public TarefaEntity cadastrarTarefa(EscalaRequestDTO infoEscala, UserEntity usuarioDestino, AudienciaEntity audiencia, String token) {


        try {
            TarefaEntity novaTarefa = new TarefaEntity();

            SetorStrategy strategy = setorStrategyFactory.getStrategy(usuarioDestino);

            Long setorDestinoId = infoEscala.isDistribuicaoManualSetores() ?
                    infoEscala.getSetorDestinoId() :
                    strategy.getSetorId(usuarioDestino, audiencia);

            Long tarefaId = sapiensClient.cadastrarTarefaSapiens(
                    usuarioDestino,
                    audiencia,
                    infoEscala.getSetorOrigemId(),
                    infoEscala.getEspecieTarefaId(),
                    setorDestinoId,
                    token
            );

            novaTarefa.setTarefaId(tarefaId);
            novaTarefa.setDestinatario(usuarioDestino);

            return tarefaRepository.save(novaTarefa);

        } catch (Exception e) {
            return null;
        }
    }

}
