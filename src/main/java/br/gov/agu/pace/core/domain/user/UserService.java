package br.gov.agu.pace.core.domain.user;

import br.gov.agu.pace.auth.dtos.SetorDTO;
import br.gov.agu.pace.client.SapiensClient;
import br.gov.agu.pace.core.domain.enums.UserRole;
import br.gov.agu.pace.core.domain.setor.SetorEntity;
import br.gov.agu.pace.core.domain.setor.SetorService;
import br.gov.agu.pace.core.domain.unidade.UnidadeEntity;
import br.gov.agu.pace.core.domain.unidade.UnidadeService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
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

    public UserEntity buscarOuCriarUsuario(String token) {

        DecodedJWT jwt = JWT.decode(token);
        Long userId = jwt.getClaim("id").asLong();

        return userRepository.findById(userId)
                .map(this::atualizarUltimoAcesso)
                .orElseGet(() -> criarNovoUsuario(jwt, token));
    }

    private UserEntity atualizarUltimoAcesso(UserEntity usuario) {
        usuario.setUltimoAcesso(LocalDateTime.now());
        return userRepository.save(usuario);
    }

    public UserEntity criarNovoUsuario(DecodedJWT tokenDecodificado, String token) {

        UserEntity novoUsuario = new UserEntity();

        novoUsuario.setSapiensId(tokenDecodificado.getClaim("id").asLong());
        novoUsuario.setEmail(tokenDecodificado.getClaim("email").asString());
        novoUsuario.setNome(tokenDecodificado.getClaim("nome").asString());

        // Consulta dados no SAPIENS (setor + unidade)
        SetorDTO dadosSetor = sapiensClient.getInformacoesSetorPorId(
                novoUsuario.getSapiensId(),
                token
        );

        SetorEntity setor = setorService.buscarOuCriarSetorPorId(dadosSetor);
        novoUsuario.setSetor(setor);

        UnidadeEntity unidade = unidadeService.buscarOuCriarUnidadePorId(dadosSetor);
        novoUsuario.setUnidade(unidade);

        novoUsuario.setRole(UserRole.USER);

        return userRepository.save(novoUsuario);
    }
}
