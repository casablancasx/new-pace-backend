package br.gov.agu.pace.domain.assunto;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AssuntoService {

    private final AssuntoRepository repository;

    @Transactional
    public AssuntoEntity buscarOuCriarAssunto(String nome) {
        if (nome == null || nome.isBlank()) {
            return null;
        }
        return repository.findByNome(nome)
                .orElseGet(() -> criarNovoAssunto(nome));
    }

    private AssuntoEntity criarNovoAssunto(String nome) {
        AssuntoEntity novoAssunto = new AssuntoEntity();
        novoAssunto.setNome(nome);
        return repository.save(novoAssunto);
    }

    public Page<AssuntoEntity> listarAssuntos(int page, int size, String nome, String orderBy, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sort), orderBy));
        return repository.buscarAssuntos(nome, pageable);
    }
}
