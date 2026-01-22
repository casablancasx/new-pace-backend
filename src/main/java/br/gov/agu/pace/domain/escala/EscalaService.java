package br.gov.agu.pace.domain.escala;

import br.gov.agu.pace.auth.service.TokenService;
import br.gov.agu.pace.domain.audiencia.entity.AudienciaEntity;
import br.gov.agu.pace.domain.audiencia.repository.AudienciaRepository;

import br.gov.agu.pace.domain.enums.TipoEscala;
import br.gov.agu.pace.domain.pauta.entity.PautaEntity;
import br.gov.agu.pace.domain.pauta.repository.PautaRepository;
import br.gov.agu.pace.domain.user.avaliador.AvaliadorService;
import br.gov.agu.pace.domain.user.UserEntity;
import br.gov.agu.pace.domain.user.UserRepository;
import br.gov.agu.pace.domain.user.UserService;
import br.gov.agu.pace.domain.user.pautista.PautistaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EscalaService {

    private final PautaRepository pautaRepository;
    private final AvaliadorService avaliadorService;
    private final PautistaService pautistaService;
    private final AudienciaRepository audienciaRepository;
    private final CadastrarTarefaService cadastrarTarefaService;
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final EscalaRepository escalaRepository;


    @Transactional
    public EscalaResponseDTO escalarAvaliadores(EscalaRequestDTO data, String token){

        UserEntity criador = userService.buscarUsuarioPorSapiensId(tokenService.getSapiensIdFromToken(token));
        Set<PautaEntity> pautas = pautaRepository.buscarPautasSemAvaliadoresEscalados(
                data.getDataInicio(),
                data.getDataFim(),
                data.getUfs(),
                data.getOrgaoJulgadorIds(),
                data.getTipoContestacao()
        );

        List<UserEntity> avaliadores = avaliadorService.buscarAvaliadoresPorIds(data.getAvaliadorIds());

        for (PautaEntity pauta : pautas) {

            //Gerenciamento de token para situacoes onde usuario selecionar muitas audiencias
            token = tokenService.renovarTokenSeExpirado(token);
            String finalToken = token;

            UserEntity avaliadorSelecionado = avaliadorService.selecionarAvaliador(avaliadores);


            List<AudienciaEntity> audiencias = pauta.getAudiencias()
                    .stream()
                    .filter(a -> data.getTipoContestacao().contains(a.getTipoContestacao()))
                    .map(a -> cadastrarTarefaService.cadastrarTarefa(data.getSetorOrigemId(),data.getEspecieTarefaId(),avaliadorSelecionado,a, finalToken))
                    .toList();


            avaliadorSelecionado.incrementarAudiencias(audiencias);
            avaliadorSelecionado.incrementarPautas();
            userRepository.save(avaliadorSelecionado);
            audienciaRepository.saveAll(audiencias);

            EscalaEntity novaEscala = new EscalaEntity();
            novaEscala.setTipo(TipoEscala.AVALIADOR);
            novaEscala.setCriador(criador);
            novaEscala.setPauta(pauta);
            novaEscala.setUsuario(avaliadorSelecionado);
            escalaRepository.save(novaEscala);
        }



        return new EscalaResponseDTO("audiencias processadas: " + pautas.size());
    }


    public EscalaResponseDTO escalarPautistas(EscalaRequestDTO dto, String token) {
        UserEntity criador = userService.buscarUsuarioPorSapiensId(tokenService.getSapiensIdFromToken(token));
        Set<PautaEntity> pautas = pautaRepository.buscarPautasSemAvaliadoresEscalados(
                dto.getDataInicio(),
                dto.getDataFim(),
                dto.getUfs(),
                dto.getOrgaoJulgadorIds(),
                dto.getTipoContestacao()
        );

        List<UserEntity> pautistas = pautistaService.buscarPautistasPorIds(dto.getPautistaIds());

        for (PautaEntity pauta : pautas) {

            //Gerenciamento de token para situacoes onde usuario selecionar muitas audiencias
            token = tokenService.renovarTokenSeExpirado(token);
            String finalToken = token;

            UserEntity avaliadorSelecionado = pautistaService.selecionarPautista(pautistas, pauta.getData());


            List<AudienciaEntity> audiencias = pauta.getAudiencias()
                    .stream()
                    .filter(a -> dto.getTipoContestacao().contains(a.getTipoContestacao()))
                    .map(a -> cadastrarTarefaService.cadastrarTarefa(dto.getSetorOrigemId(),dto.getEspecieTarefaId(),avaliadorSelecionado,a, finalToken))
                    .toList();


            avaliadorSelecionado.incrementarAudiencias(audiencias);
            avaliadorSelecionado.incrementarPautas();
            userRepository.save(avaliadorSelecionado);
            audienciaRepository.saveAll(audiencias);

            EscalaEntity novaEscala = new EscalaEntity();
            novaEscala.setTipo(TipoEscala.PAUTISTA);
            novaEscala.setCriador(criador);
            novaEscala.setPauta(pauta);
            novaEscala.setUsuario(avaliadorSelecionado);
        }



        return new EscalaResponseDTO("audiencias processadas:");


    }
}
