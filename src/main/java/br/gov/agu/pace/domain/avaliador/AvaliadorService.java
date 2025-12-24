package br.gov.agu.pace.domain.avaliador;

import br.gov.agu.pace.domain.setor.SetorEntity;
import br.gov.agu.pace.domain.setor.SetorService;
import br.gov.agu.pace.domain.unidade.UnidadeEntity;
import br.gov.agu.pace.domain.unidade.UnidadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliadorService {

    private final AvaliadorRepository repository;
    private final SetorService setorService;
    private final UnidadeService unidadeService;




    public AvaliadorEntity selecionarAvaliador(final List<AvaliadorEntity> avaliadores) {
        return avaliadores.stream()
                .min(Comparator.comparingLong(AvaliadorEntity::calcularCargaTrabalho))
                .orElseThrow(() -> new RuntimeException("Nenhum avaliador disponivel para escala"));
    }

    public AvaliadorEntity cadastrarAvaliador(AvaliadorRequestDTO dto) {
        AvaliadorEntity avaliador = new AvaliadorEntity();
        avaliador.setNome(dto.getNome());
        avaliador.setEmail(dto.getEmail());
        avaliador.setDisponivel(true);
        avaliador.setSapiensId(dto.getSapiensId());
        SetorEntity setor = setorService.buscarOuCriarSetorPorId(dto.getSetor());
        avaliador.setSetor(setor);
        UnidadeEntity unidade = unidadeService.buscarOuCriarUnidadePorId(dto.getSetor());
        avaliador.setUnidade(unidade);
        return repository.save(avaliador);

    }

    public Page<AvaliadorEntity> listarAvaliadores(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }
}
