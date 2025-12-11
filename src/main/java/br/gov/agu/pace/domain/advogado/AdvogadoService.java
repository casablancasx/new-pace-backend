package br.gov.agu.pace.domain.advogado;

import br.gov.agu.pace.domain.uf.UfEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvogadoService {

    private final AdvogadoRepository repository;

    /**
     * Busca ou cria um advogado pelo nome.
     * Advogados criados a partir do upload da planilha são salvos como NÃO prioritários.
     */
    @Transactional
    public AdvogadoEntity buscarOuCriarAdvogado(String nome, UfEntity uf) {
        if (nome == null || nome.isBlank()) {
            return null;
        }
        return repository.findByNome(nome)
                .orElseGet(() -> criarNovoAdvogado(nome, uf));
    }

    /**
     * Cria um novo advogado como NÃO prioritário (vindo do upload da planilha).
     */
    private AdvogadoEntity criarNovoAdvogado(String nome, UfEntity uf) {
        AdvogadoEntity novoAdvogado = new AdvogadoEntity();
        novoAdvogado.setNome(nome);
        novoAdvogado.setPrioritario(false); // Advogados do upload NÃO são prioritários
        novoAdvogado.setUfs(uf != null ? List.of(uf) : new ArrayList<>());
        novoAdvogado.setAudiencias(new ArrayList<>());
        return repository.save(novoAdvogado);
    }

    /**
     * Resolve uma lista de nomes de advogados para entidades.
     */
    @Transactional
    public List<AdvogadoEntity> resolverAdvogados(List<String> nomes, UfEntity uf) {
        if (nomes == null || nomes.isEmpty()) {
            return new ArrayList<>();
        }
        return nomes.stream()
                .filter(nome -> nome != null && !nome.isBlank())
                .map(nome -> buscarOuCriarAdvogado(nome, uf))
                .toList();
    }
}
