package br.gov.agu.pace.escala;

import br.gov.agu.pace.auth.service.TokenService;
import br.gov.agu.pace.cadadastroTarefaSapiens.CadastrarTarefaService;
import br.gov.agu.pace.domain.audiencia.entity.AudienciaEntity;
import br.gov.agu.pace.domain.audiencia.repository.AudienciaRepository;
import br.gov.agu.pace.domain.avaliador.AvaliadorEntity;
import br.gov.agu.pace.domain.avaliador.AvaliadorRepository;
import br.gov.agu.pace.domain.avaliador.AvaliadorService;
import br.gov.agu.pace.domain.pauta.entity.PautaEntity;
import br.gov.agu.pace.domain.pauta.repository.PautaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EscalaService {

    private final PautaRepository pautaRepository;
    private final AvaliadorRepository avaliadorRepository;
    private final AvaliadorService avaliadorService;
    private final AudienciaRepository audienciaRepository;
    private final CadastrarTarefaService cadastrarTarefaService;
    private final TokenService tokenService;


    public EscalaResponseDTO escalarAvaliadores(EscalaRequestDTO data, String token){


        List<PautaEntity> pautas = pautaRepository.buscarPautasSemAvaliadoresEscalados(
                data.getDataInicio(),
                data.getDataFim(),
                data.getUfs(),
                data.getOrgaoJulgadorIds(),
                data.getTipoContestacao()
        );

        List<AvaliadorEntity> avaliadores = data.getAvaliadorIds().isEmpty()?
                avaliadorRepository.findAll() :
                avaliadorRepository.buscarAvaliadoresPorIds(data.getAvaliadorIds());

        for (PautaEntity pauta : pautas) {

            token = tokenService.renovarTokenSeExpirado(token);
            String finalToken = token;

            AvaliadorEntity avaliadorSelecionado = avaliadorService.selecionarAvaliador(avaliadores);


            List<AudienciaEntity> audiencias = pauta.getAudiencias()
                    .stream()
                    .filter(a -> data.getTipoContestacao().contains(a.getTipoContestacao()))
                    .map(a -> cadastrarTarefaService.cadastrarTarefa(data.getSetorOrigemId(),data.getEspecieTarefaId(),avaliadorSelecionado,a, finalToken))
                    .toList();


            audiencias.forEach(a -> a.setAvaliador(avaliadorSelecionado));

            avaliadorSelecionado.incrementarAudiencias(audiencias);
            avaliadorSelecionado.incrementarPautas();
            avaliadorRepository.save(avaliadorSelecionado);
            audienciaRepository.saveAll(audiencias);
        }


        Long sucesso = pautas.stream().mapToLong(PautaEntity::getTotalAudienciasCadastradasComSucesso).sum();
        Long falha = pautas.stream().mapToLong(PautaEntity::getTotalAudienciasCadastradasComErro).sum();

        Long total = sucesso + falha;

        String mensagem = String.format(
                "Audiências processadas: %d. Tarefas cadastradas: %d. Erro durante cadastro: %d.",
                total,
                sucesso,
                falha
        );

        return new EscalaResponseDTO(mensagem);
    }



}
