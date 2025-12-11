package br.gov.agu.pace.domain.orgaoJulgador;

import br.gov.agu.pace.domain.uf.UfEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrgaoJulgadorService {

    private final OrgaoJulgadorRepository repository;

    @Transactional
    public OrgaoJulgadorEntity buscarOuCriarOrgaoJulgador(String nome, UfEntity uf) {
        return repository.findByNome(nome)
                .orElseGet(() -> criarNovoOrgaoJulgador(nome, uf));
    }

    private OrgaoJulgadorEntity criarNovoOrgaoJulgador(String nome, UfEntity uf) {
        OrgaoJulgadorEntity novoOrgao = new OrgaoJulgadorEntity();
        novoOrgao.setNome(nome);
        novoOrgao.setUf(uf);
        return repository.save(novoOrgao);
    }
}
