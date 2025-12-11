package br.gov.agu.pace.domain.user;

import br.gov.agu.pace.auth.dtos.UserFromTokenDTO;
import br.gov.agu.pace.integrations.client.SapiensClient;
import br.gov.agu.pace.integrations.dtos.SetorDTO;
import br.gov.agu.pace.domain.enums.UserRole;
import br.gov.agu.pace.domain.setor.SetorEntity;
import br.gov.agu.pace.domain.setor.SetorService;
import br.gov.agu.pace.domain.unidade.UnidadeEntity;
import br.gov.agu.pace.domain.unidade.UnidadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SetorService setorService;
    private final SapiensClient sapiensClient;
    private final UnidadeService unidadeService;

    public UserEntity buscarOuCriarUsuario(UserFromTokenDTO userFromTokenDTO) {
        return userRepository.findById(userFromTokenDTO.getSapiensId())
                .map(this::atualizarUltimoAcesso)
                .orElseGet(() -> criarNovoUsuario(userFromTokenDTO));
    }

    private UserEntity atualizarUltimoAcesso(UserEntity usuario) {
        usuario.setUltimoAcesso(LocalDateTime.now());
        return userRepository.save(usuario);
    }

    public UserEntity criarNovoUsuario(UserFromTokenDTO userFromTokenDTO) {

        UserEntity novoUsuario = new UserEntity();

        novoUsuario.setSapiensId(userFromTokenDTO.getSapiensId());
        novoUsuario.setEmail(userFromTokenDTO.getEmail());
        novoUsuario.setNome(userFromTokenDTO.getNome());

        // Consulta dados no SAPIENS (setor + unidade)
        SetorDTO dadosSetor = sapiensClient.getInformacoesSetorPorId(
                userFromTokenDTO.getSapiensId(),
                userFromTokenDTO.getToken()
        );

        SetorEntity setor = setorService.buscarOuCriarSetorPorId(dadosSetor);
        novoUsuario.setSetor(setor);

        UnidadeEntity unidade = unidadeService.buscarOuCriarUnidadePorId(dadosSetor);
        novoUsuario.setUnidade(unidade);

        novoUsuario.setRole(UserRole.USER);

        return userRepository.save(novoUsuario);
    }
}
