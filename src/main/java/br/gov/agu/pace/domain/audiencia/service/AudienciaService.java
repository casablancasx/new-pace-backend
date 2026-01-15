package br.gov.agu.pace.domain.audiencia.service;

import br.gov.agu.pace.auth.dtos.UserFromTokenDTO;
import br.gov.agu.pace.auth.service.TokenService;
import br.gov.agu.pace.commons.exceptions.UserUnauthorizedException;
import br.gov.agu.pace.domain.audiencia.dtos.AnalisarAudienciaDTO;
import br.gov.agu.pace.domain.audiencia.entity.AudienciaEntity;
import br.gov.agu.pace.domain.audiencia.repository.AudienciaRepository;

import br.gov.agu.pace.domain.enums.RespostaAnaliseAvaliador;
import br.gov.agu.pace.domain.enums.UserRole;
import br.gov.agu.pace.domain.user.UserEntity;
import br.gov.agu.pace.domain.user.UserRepository;
import br.gov.agu.pace.domain.user.UserService;
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
    private final UserRepository userRepository;
    private final UserService userService;


    public Page<AudienciaEntity> listarAudiencias(int page, int size, String numeroProcesso, Long orgaoJulgadorId, Sort.Direction sort, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort, orderBy));
        return repository.listarAudiencias(numeroProcesso,orgaoJulgadorId,pageable);
    }



    public AudienciaEntity analisarAudiencia(AnalisarAudienciaDTO data,String token) {


        UserEntity avaliador = userService.buscarUsuarioPorSapiensId(tokenService.getSapiensIdFromToken(token));

        if (!avaliador.getRole().equals(UserRole.AVALIADOR) && !avaliador.getRole().equals(UserRole.ADMIN)){
            throw new UserUnauthorizedException("Seu usuário não possui permissão para realizar esta ação");
        }

        AudienciaEntity audiencia = repository.findById(data.getAudienciaId()).orElseThrow();
        audiencia.setObservacao(data.getObservacao());
        audiencia.setAnaliseAvaliador(data.getResposta());
        audiencia.setClasseJudicial(data.getClasseJudicial());
        audiencia.setSubnucleo(data.getSubnucleo());
        audiencia.setTipoContestacao(data.getTipoContestacao());

        if (!data.getResposta().equals(RespostaAnaliseAvaliador.ANALISE_PENDENTE)){
            avaliador.incrementarQuantidadeAudienciasAnalisadas();
            userRepository.save(avaliador);
        }

        return repository.save(audiencia);
    }
}
