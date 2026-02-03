package br.gov.agu.pace.domain.escala;

import br.gov.agu.pace.auth.service.TokenService;
import br.gov.agu.pace.domain.audiencia.entity.AudienciaEntity;
import br.gov.agu.pace.domain.audiencia.repository.AudienciaRepository;
import br.gov.agu.pace.domain.enums.ClasseJudicial;
import br.gov.agu.pace.domain.enums.TipoEscala;
import br.gov.agu.pace.domain.pauta.entity.PautaEntity;
import br.gov.agu.pace.domain.pauta.repository.PautaRepository;
import br.gov.agu.pace.domain.user.UserEntity;
import br.gov.agu.pace.domain.user.UserRepository;
import br.gov.agu.pace.domain.user.UserService;
import br.gov.agu.pace.domain.user.avaliador.AvaliadorService;
import br.gov.agu.pace.domain.user.pautista.PautistaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class EscalaService {

    private final PautaRepository pautaRepository;
    private final AvaliadorService avaliadorService;
    private final CadastrarTarefaService cadastrarTarefaService;
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final EscalaRepository escalaRepository;


    public EscalaResponseDTO escalarAvaliadores(EscalaRequestDTO infoEscala, String token) {

        UserEntity criador = getCriador(token);
        Set<PautaEntity> pautas = getBuscarPautasSemAvaliadoresEscalados(infoEscala);

        List<UserEntity> avaliadores = avaliadorService.buscarAvaliadoresPorIds(infoEscala.getAvaliadorIds());

        Map<String, Integer> resultado = Map.of();

        for (PautaEntity pauta : pautas) {

            //Gerenciamento de token para situacoes onde usuario selecionar muitas audiencias
            token = tokenService.renovarTokenSeExpirado(token);
            String finalToken = token;
            resultado = processarPauta(infoEscala, pauta, avaliadores, criador, finalToken);

        }


        return new EscalaResponseDTO(
                "Audiencias processadas com sucesso",
                resultado.get("sucesso"),
                resultado.get("falha"),
                pautas.stream().map(PautaEntity::getAudiencias).toList().size()
        );
    }

    private Map<String, Integer> processarPauta(EscalaRequestDTO infoEscala, PautaEntity pauta, List<UserEntity> avaliadores, UserEntity criador, String finalToken) {

        int sucesso = 0;
        int falha = 0;

        UserEntity avaliadorSelecionado = avaliadorService.selecionarAvaliador(avaliadores);

        List<AudienciaEntity> audienciasFiltradas = pauta.getAudiencias()
                .stream()
                .filter(a -> a.getClasseJudicial().equals(ClasseJudicial.COMUM))
                .filter(a -> infoEscala.getTipoContestacao().contains(a.getTipoContestacao()))
                .toList();

        for (AudienciaEntity audiencia : audienciasFiltradas) {
            EscalaEntity novaEscala = new EscalaEntity();
            novaEscala.setTipo(TipoEscala.AVALIADOR);
            novaEscala.setUsuario(avaliadorSelecionado);
            novaEscala.setCriador(criador);

            var tarefa = cadastrarTarefaService.cadastrarTarefa(infoEscala, avaliadorSelecionado, audiencia, finalToken);

            if (tarefa == null) {
                log.error("Erro ao cadastrar tarefa no Sapiens audiencia {}", audiencia.getNumeroProcesso());
                falha++;
                continue;
            }

            novaEscala.setTarefa(tarefa);
            escalaRepository.save(novaEscala);
            sucesso++;
        }

        avaliadorSelecionado.incrementarAudiencias(audienciasFiltradas);
        avaliadorSelecionado.incrementarPautas();

        userRepository.save(avaliadorSelecionado);

        return Map.of("sucesso", sucesso, "falha", falha);
    }

    private Set<PautaEntity> getBuscarPautasSemAvaliadoresEscalados(EscalaRequestDTO infoEscala) {
        return pautaRepository.buscarPautasSemAvaliadoresEscalados(
                infoEscala.getDataInicio(),
                infoEscala.getDataFim(),
                infoEscala.getUfs(),
                infoEscala.getOrgaoJulgadorIds(),
                infoEscala.getTipoContestacao()
        );
    }

    private UserEntity getCriador(String token) {
        return userService.buscarUsuarioPorSapiensId(tokenService.getSapiensIdFromToken(token));
    }


//    public EscalaResponseDTO escalarPautistas(EscalaRequestDTO dto, String token) {
//        UserEntity criador = getCriador(token);
//        Set<PautaEntity> pautas = getBuscarPautasSemAvaliadoresEscalados(dto);
//
//        List<UserEntity> pautistas = pautistaService.buscarPautistasPorIds(dto.getPautistaIds());
//
//        for (PautaEntity pauta : pautas) {
//
//            //Gerenciamento de token para situacoes onde usuario selecionar muitas audiencias
//            token = tokenService.renovarTokenSeExpirado(token);
//            String finalToken = token;
//
//            UserEntity avaliadorSelecionado = pautistaService.selecionarPautista(pautistas, pauta.getData());
//
//
//            List<AudienciaEntity> audiencias = pauta.getAudiencias()
//                    .stream()
//                    .filter(a -> dto.getTipoContestacao().contains(a.getTipoContestacao()))
//                    .map(a -> cadastrarTarefaService.cadastrarTarefa(dto.getSetorOrigemId(), dto.getEspecieTarefaId(), avaliadorSelecionado, a, finalToken))
//                    .toList();
//
//
//            avaliadorSelecionado.incrementarAudiencias(audiencias);
//            avaliadorSelecionado.incrementarPautas();
//            userRepository.save(avaliadorSelecionado);
//            audienciaRepository.saveAll(audiencias);
//
//            EscalaEntity novaEscala = new EscalaEntity();
//            novaEscala.setTipo(TipoEscala.PAUTISTA);
//            novaEscala.setCriador(criador);
//            novaEscala.setUsuario(avaliadorSelecionado);
//            escalaRepository.save(novaEscala);
//        }
//
//
//        return new EscalaResponseDTO("audiencias processadas:");
//
//    }
}
