package br.gov.agu.pace.domain.audiencia.service;

import br.gov.agu.pace.auth.dtos.UserFromTokenDTO;
import br.gov.agu.pace.auth.service.TokenService;
import br.gov.agu.pace.domain.audiencia.dtos.AnalisarAudienciaDTO;
import br.gov.agu.pace.domain.audiencia.entity.AudienciaEntity;
import br.gov.agu.pace.domain.audiencia.repository.AudienciaRepository;
import br.gov.agu.pace.domain.avaliador.AvaliadorEntity;
import br.gov.agu.pace.domain.avaliador.AvaliadorRepository;
import br.gov.agu.pace.domain.enums.RespostaAnaliseAvaliador;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AudienciaService {
    private final AudienciaRepository repository;
    private final TokenService tokenService;
    private final AvaliadorRepository avaliadorRepository;


    public Page<AudienciaEntity> listarAudiencias(int page, int size, String numeroProcesso, Long orgaoJulgadorId, Sort.Direction sort, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort, orderBy));
        return repository.listarAudiencias(numeroProcesso,orgaoJulgadorId,pageable);
    }



    public AudienciaEntity analisarAudiencia(AnalisarAudienciaDTO data,String token) {
        UserFromTokenDTO userFromTokenDTO = tokenService.getUserFromToken(token);
        AvaliadorEntity avaliador = avaliadorRepository.findById(userFromTokenDTO.getSapiensId()).orElse(null);
        AudienciaEntity audiencia = repository.findById(data.getAudienciaId()).orElseThrow();
        audiencia.setObservacao(data.getObservacao());
        audiencia.setAnaliseAvaliador(data.getResposta());

        //Permite que ADMIN possa alterar resultado da analise de audiencia sem necessidade de ser um avaliador
        if (!data.getResposta().equals(RespostaAnaliseAvaliador.ANALISE_PENDENTE) && avaliador != null){
            avaliador.incrementarQuantidadeAudienciasAnalisadas();
            avaliadorRepository.save(avaliador);
        }

        return repository.save(audiencia);
    }
}
