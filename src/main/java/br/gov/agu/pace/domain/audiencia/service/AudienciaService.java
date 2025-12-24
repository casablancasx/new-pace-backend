package br.gov.agu.pace.domain.audiencia.service;

import br.gov.agu.pace.domain.audiencia.entity.AudienciaEntity;
import br.gov.agu.pace.domain.audiencia.repository.AudienciaRepository;
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


    public Page<AudienciaEntity> listarAudiencias(int page, int size, String numeroProcesso, Long orgaoJulgadorId, Sort.Direction sort, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort, orderBy));
        return repository.listarAudiencias(numeroProcesso,orgaoJulgadorId,pageable);
    }

    public AudienciaEntity atualizarAudiencia(AudienciaEntity audienciaAtualizada) {
        return repository.save(audienciaAtualizada);
    }
}
