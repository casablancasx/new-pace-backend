package br.gov.agu.pace.domain.assunto;

import lombok.RequiredArgsConstructor;
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
}
