package br.gov.agu.pace.domain.pauta.service;

import br.gov.agu.pace.domain.advogado.AdvogadoEntity;
import br.gov.agu.pace.domain.advogado.AdvogadoService;
import br.gov.agu.pace.domain.assunto.AssuntoEntity;
import br.gov.agu.pace.domain.assunto.AssuntoService;
import br.gov.agu.pace.domain.audiencia.entity.AudienciaEntity;
import br.gov.agu.pace.domain.audiencia.mapper.AudienciaMapper;
import br.gov.agu.pace.domain.audiencia.repository.AudienciaRepository;
import br.gov.agu.pace.domain.enums.Turno;
import br.gov.agu.pace.domain.enums.Uf;
import br.gov.agu.pace.domain.orgaoJulgador.OrgaoJulgadorEntity;
import br.gov.agu.pace.domain.orgaoJulgador.OrgaoJulgadorService;
import br.gov.agu.pace.domain.pauta.dtos.PautaDTO;
import br.gov.agu.pace.domain.pauta.entity.PautaEntity;
import br.gov.agu.pace.domain.pauta.mapper.PautaMapper;
import br.gov.agu.pace.domain.pauta.repository.PautaRepository;
import br.gov.agu.pace.domain.sala.SalaEntity;
import br.gov.agu.pace.domain.sala.SalaService;
import br.gov.agu.pace.domain.uf.UfEntity;
import br.gov.agu.pace.domain.uf.UfService;
import br.gov.agu.pace.domain.planilha.dtos.AudienciaDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PautaService {

    private final PautaRepository pautaRepository;
    private final AudienciaRepository audienciaRepository;
    private final PautaMapper pautaMapper;
    private final AudienciaMapper audienciaMapper;
    
    private final SalaService salaService;
    private final UfService ufService;
    private final OrgaoJulgadorService orgaoJulgadorService;
    private final AssuntoService assuntoService;
    private final AdvogadoService advogadoService;

    public Map<PautaDTO, List<AudienciaDTO>> agruparAudienciasPorPauta(Set<AudienciaDTO> audiencias) {
        return audiencias.stream()
                .collect(Collectors.groupingBy(
                        a -> new PautaDTO(a.getData(), a.getTurno().getDescricao(), a.getSala(), a.getOrgaoJulgador(), a.getUf())
                ));
    }


    private PautaEntity buscarPautaExistente(LocalDate data, Turno turno, SalaEntity sala, OrgaoJulgadorEntity orgaoJulgador) {
        return pautaRepository.findByDataAndTurnoAndSalaAndOrgaoJulgador(data, turno, sala, orgaoJulgador)
                .orElse(null);
    }

    @Transactional
    public void salvarPautas(Set<AudienciaDTO> audienciasDTO) {
        log.info("Iniciando persistência de {} audiências", audienciasDTO.size());
        
        Map<PautaDTO, List<AudienciaDTO>> pautasAgrupadas = agruparAudienciasPorPauta(audienciasDTO);
        log.info("Audiências agrupadas em {} pautas", pautasAgrupadas.size());
        
        List<PautaEntity> pautasParaSalvar = new ArrayList<>();
        List<AudienciaEntity> audienciasParaSalvar = new ArrayList<>();
        
        pautasAgrupadas.forEach((pautaDTO, audienciasDaPauta) -> {
            // Resolver/criar entidades relacionadas à pauta
            UfEntity uf = ufService.buscarOuCriarUf(pautaDTO.getUf());
            SalaEntity sala = salaService.buscarSalaPorNome(pautaDTO.getSala());
            OrgaoJulgadorEntity orgaoJulgador = orgaoJulgadorService.buscarOuCriarOrgaoJulgador(
                    pautaDTO.getOrgaoJulgador(), uf);
            
            // Verificar se a pauta já existe
            PautaEntity pauta = buscarPautaExistente(pautaDTO.getData(), Turno.fromString(pautaDTO.getTurno()), sala, orgaoJulgador);
            
            boolean isPautaNova = (pauta == null);
            if (isPautaNova) {
                pauta = pautaMapper.toEntity(pautaDTO, sala, orgaoJulgador);
                pautasParaSalvar.add(pauta);
            }
            
            // Processar audiências desta pauta
            final PautaEntity pautaFinal = pauta;
            PautaEntity finalPauta = pauta;
            audienciasDaPauta.forEach(audienciaDTO -> {
                // Resolver assunto
                AssuntoEntity assunto = assuntoService.buscarOuCriarAssunto(audienciaDTO.getAssunto());
                
                // Resolver advogados (NÃO prioritários quando vêm do upload)
                List<AdvogadoEntity> advogados = advogadoService.resolverAdvogados(
                        audienciaDTO.getAdvogados(), uf);
                
                // Verificar se a audiência já existe na pauta
                Optional<AudienciaEntity> audienciaExistente = Optional.empty();
                if (!isPautaNova) {
                    audienciaExistente = audienciaRepository
                            .findByNumeroProcessoAndPauta(audienciaDTO.getNumeroProcesso(), pautaFinal);
                }
                
                AudienciaEntity audiencia;
                if (audienciaExistente.isPresent()) {
                    // Atualizar audiência existente se houver alterações
                    audiencia = audienciaExistente.get();
                    boolean alterada = atualizarAudienciaSeNecessario(audiencia, audienciaDTO, assunto, advogados);
                    if (alterada) {
                        audienciasParaSalvar.add(audiencia);
                        log.debug("Audiência {} atualizada", audienciaDTO.getNumeroProcesso());
                    } else {
                        log.debug("Audiência {} sem alterações", audienciaDTO.getNumeroProcesso());
                    }
                } else {
                    // Criar nova audiência
                    audiencia = audienciaMapper.toEntity(audienciaDTO, pautaFinal, assunto, advogados);
                    audiencia.setNovaAudiencia(true);
                    audienciasParaSalvar.add(audiencia);
                    log.debug("Nova audiência {} adicionada", audienciaDTO.getNumeroProcesso());
                }
            });
        });
        
        // Salvar pautas em batch
        if (!pautasParaSalvar.isEmpty()) {
            pautaRepository.saveAll(pautasParaSalvar);
            log.info("{} novas pautas salvas", pautasParaSalvar.size());
        }
        
        // Salvar audiências em batch
        if (!audienciasParaSalvar.isEmpty()) {
            audienciaRepository.saveAll(audienciasParaSalvar);
            log.info("{} audiências salvas/atualizadas", audienciasParaSalvar.size());
        }
        
        log.info("Persistência concluída com sucesso");
    }


    private boolean atualizarAudienciaSeNecessario(AudienciaEntity audiencia, AudienciaDTO dto,
                                                    AssuntoEntity assunto, List<AdvogadoEntity> advogados) {
        boolean alterada = false;

        Set<AdvogadoEntity> advogadosSet = new LinkedHashSet<>(advogados);
        
        if (!Objects.equals(audiencia.getNomeParte(), dto.getPoloAtivo())) {
            audiencia.setNomeParte(dto.getPoloAtivo());
            alterada = true;
        }
        if (!Objects.equals(audiencia.getHorario(), dto.getHora())) {
            audiencia.setHorario(dto.getHora());
            alterada = true;
        }
        if (!Objects.equals(audiencia.getTipoContestacao(), dto.getTipoContestacao())) {
            audiencia.setTipoContestacao(dto.getTipoContestacao());
            alterada = true;
        }

        if (!Objects.equals(audiencia.getAssunto(), assunto)) {
            audiencia.setAssunto(assunto);
            alterada = true;
        }
        if (!Objects.equals(audiencia.getAdvogados(), advogadosSet)) {
            audiencia.setAdvogados(advogadosSet);
            alterada = true;
        }
        
        return alterada;
    }

    public Page<PautaEntity> listarTodas(int page, int size, String orderBy, Sort.Direction sort, Long orgaoJulgadorId,Long userId, Uf uf) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort, orderBy));
        Page<PautaEntity> pautasPaginadas = pautaRepository.listarPautas(orgaoJulgadorId,userId,uf,pageable);
        return pautasPaginadas;
    }

    public PautaDTO buscarPautaPorId(Long id) {
        PautaEntity pauta = pautaRepository.buscarPorId(id);
        var response = pautaMapper.toResponseDto(pauta);

        if (pauta.isPossuiNovaAudiencia()){
            pauta.getAudiencias().stream()
                    .filter(AudienciaEntity::isNovaAudiencia)
                    .forEach(a -> a.setNovaAudiencia(false));
            pautaRepository.save(pauta);
        }


        return response;
    }
}
