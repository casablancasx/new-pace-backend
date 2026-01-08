package br.gov.agu.pace.domain.user;

import br.gov.agu.pace.commons.exceptions.UserUnauthorizedException;
import br.gov.agu.pace.domain.user.dto.UsuarioSapiensDTO;
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
    private final UnidadeService unidadeService;
    private final SetorService setorService;
    private final SapiensClient sapiensClient;


    public UserEntity buscarUsuarioPorSapiensId(Long sapiensId) {
        return userRepository.findById(sapiensId).map(this::atualizarUltimoAcesso).orElseThrow(
                () -> new UserUnauthorizedException("Acesso não autorizado. Por favor, entre em contato com o administrador do sistema para solicitar a permissão necessária.")
        );
    }


    public UserEntity cadastrarUsuario(UsuarioSapiensDTO dto, String token, UserRole role){
        UserEntity avaliador = new UserEntity();
        avaliador.setSapiensId(dto.getSapiensId());
        avaliador.setNome(dto.getNome());
        avaliador.setEmail(dto.getEmail());
        avaliador.setRole(role);
        avaliador.setMetric(new UserMetricEntity(avaliador));

        //Busca informacoes do setor no sapiens
        SetorDTO dadosSetor = sapiensClient.getInformacoesSetorPorId(
                dto.getSetor().getSetorId(),
                token
        );

        //Vericar se unidade ou setor existem para persistir no banco
        UnidadeEntity unidade = unidadeService.buscarOuCriarUnidade(dadosSetor.getUnidadeId(), dadosSetor.getNomeUnidade());
        SetorEntity setor = setorService.buscarOuCriarSetor(dadosSetor.getSetorId(), dadosSetor.getNomeSetor(), unidade);
        avaliador.setSetor(setor);
        return userRepository.save(avaliador);
    }


    private UserEntity atualizarUltimoAcesso(UserEntity usuario) {
        usuario.setUltimoAcesso(LocalDateTime.now());
        return userRepository.save(usuario);
    }

}
