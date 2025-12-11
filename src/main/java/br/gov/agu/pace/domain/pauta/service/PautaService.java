package br.gov.agu.pace.domain.pauta.service;

import br.gov.agu.pace.domain.advogado.AdvogadoEntity;
import br.gov.agu.pace.domain.advogado.AdvogadoService;
import br.gov.agu.pace.domain.assunto.AssuntoEntity;
import br.gov.agu.pace.domain.assunto.AssuntoService;
import br.gov.agu.pace.domain.audiencia.entity.AudienciaEntity;
import br.gov.agu.pace.domain.audiencia.mapper.AudienciaMapper;
import br.gov.agu.pace.domain.audiencia.repository.AudienciaRepository;
import br.gov.agu.pace.domain.enums.Turno;
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
import br.gov.agu.pace.planilha.dtos.AudienciaDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    
    // Services de domínio
    private final SalaService salaService;
    private final UfService ufService;
    private final OrgaoJulgadorService orgaoJulgadorService;
    private final AssuntoService assuntoService;
    private final AdvogadoService advogadoService;

    public Map<PautaDTO, List<AudienciaDTO>> agruparAudienciasPorPauta(Set<AudienciaDTO> audiencias) {
        return audiencias.stream()
                .collect(Collectors.groupingBy(
                        a -> new PautaDTO(a.getData(), a.getTurno(), a.getSala(), a.getOrgaoJulgador(), a.getUf())
                ));
    }

    /**
     * Busca uma pauta existente ou retorna null.
     */
    private PautaEntity buscarPautaExistente(LocalDate data, Turno turno, SalaEntity sala, OrgaoJulgadorEntity orgaoJulgador) {
        return pautaRepository.findByDataAndTurnoAndSalaAndOrgaoJulgador(data, turno, sala, orgaoJulgador)
                .orElse(null);
    }

    /**
     * Salva as pautas e todas as entidades relacionadas de forma performática.
     * 
     * Ordem de persistência:
     * 1. UFs
     * 2. Salas
     * 3. Órgãos Julgadores
     * 4. Assuntos
     * 5. Advogados (salvos como NÃO prioritários quando vêm do upload)
     * 6. Pautas
     * 7. Audiências
     */
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
            
            // Verificar se a pauta já existe ou criar nova
            PautaEntity pauta = buscarPautaExistente(pautaDTO.getData(), pautaDTO.getTurno(), sala, orgaoJulgador);
            
            if (pauta == null) {
                pauta = pautaMapper.toEntity(pautaDTO, sala, orgaoJulgador);
                pautasParaSalvar.add(pauta);
            }
            
            // Processar audiências desta pauta
            final PautaEntity pautaFinal = pauta;
            audienciasDaPauta.forEach(audienciaDTO -> {
                // Resolver assunto
                AssuntoEntity assunto = assuntoService.buscarOuCriarAssunto(audienciaDTO.getAssunto());
                
                // Resolver advogados (NÃO prioritários quando vêm do upload)
                List<AdvogadoEntity> advogados = advogadoService.resolverAdvogados(
                        audienciaDTO.getAdvogados(), uf);
                
                // Criar entidade de audiência
                AudienciaEntity audiencia = audienciaMapper.toEntity(
                        audienciaDTO, pautaFinal, assunto, advogados);
                
                audienciasParaSalvar.add(audiencia);
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
            log.info("{} audiências salvas", audienciasParaSalvar.size());
        }
        
        log.info("Persistência concluída com sucesso");
    }
}
