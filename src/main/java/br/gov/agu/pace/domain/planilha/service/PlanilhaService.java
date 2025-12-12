package br.gov.agu.pace.domain.planilha.service;

import br.gov.agu.pace.auth.dtos.UserFromTokenDTO;
import br.gov.agu.pace.auth.service.TokenService;
import br.gov.agu.pace.domain.audiencia.service.ProcessarAudienciasAsync;
import br.gov.agu.pace.domain.pauta.service.PautaService;
import br.gov.agu.pace.domain.user.UserRepository;
import br.gov.agu.pace.domain.planilha.dtos.AudienciaDTO;
import br.gov.agu.pace.domain.planilha.dtos.PlanilhaDTO;
import br.gov.agu.pace.domain.planilha.entity.PlanilhaEntity;
import br.gov.agu.pace.domain.planilha.repository.PlanilhaRepository;
import br.gov.agu.pace.domain.planilha.util.FileHashUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class PlanilhaService {

    private final ExcelReaderService excelReaderService;
    private final PautaService pautaService;
    private final ProcessarAudienciasAsync async;
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final PlanilhaRepository planilhaRepository;

    public PlanilhaDTO importarPlanilha(MultipartFile file, String token) throws Exception {
        PlanilhaEntity planilha = new PlanilhaEntity();
        planilha.setNomeArquivo(file.getOriginalFilename());
        String hash = FileHashUtil.getFileHash(file, "SHA-256");
        planilha.setHash(hash);
        UserFromTokenDTO userFromToken = tokenService.getUserFromToken(token);
        userRepository.findById(userFromToken.getSapiensId()).ifPresent(planilha::setUsuario);
        planilhaRepository.save(planilha);


        Set<AudienciaDTO> audiencias = excelReaderService.importarPlanilha(file);

        async.processarAudiencias(token, audiencias, planilha);



        var pautas = pautaService.agruparAudienciasPorPauta(audiencias);

        return PlanilhaDTO.builder()
                .message("Planilha importada com sucesso")
                .file(file.getOriginalFilename())
                .totalAudiencias(audiencias.size())
                .totalPautas(pautas.size())
                .build();
    }



}
