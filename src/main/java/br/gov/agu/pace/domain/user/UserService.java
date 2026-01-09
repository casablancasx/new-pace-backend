package br.gov.agu.pace.domain.user;

import br.gov.agu.pace.commons.exceptions.UserUnauthorizedException;
import br.gov.agu.pace.domain.user.dto.UsuarioResponseDTO;
import br.gov.agu.pace.domain.user.dto.UsuarioSapiensDTO;
import br.gov.agu.pace.integrations.client.SapiensClient;
import br.gov.agu.pace.integrations.dtos.SetorDTO;
import br.gov.agu.pace.domain.enums.UserRole;
import br.gov.agu.pace.domain.setor.SetorEntity;
import br.gov.agu.pace.domain.setor.SetorService;
import br.gov.agu.pace.domain.unidade.UnidadeEntity;
import br.gov.agu.pace.domain.unidade.UnidadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UnidadeService unidadeService;
    private final SetorService setorService;
    private final SapiensClient sapiensClient;
    private final UserMapper mapper;


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
        UnidadeEntity unidade = unidadeService.salvarUnidade(dadosSetor.getUnidadeId(), dadosSetor.getNomeUnidade());
        SetorEntity setor = setorService.salvarSetor(dadosSetor.getSetorId(), dadosSetor.getNomeSetor(), unidade);
        avaliador.setSetor(setor);
        return userRepository.save(avaliador);
    }


    private UserEntity atualizarUltimoAcesso(UserEntity usuario) {
        usuario.setUltimoAcesso(LocalDateTime.now());
        return userRepository.save(usuario);
    }

    public Page<UsuarioResponseDTO> listarUsuarios(int pageIndex, int size, String nome) {
        Pageable pageable = PageRequest.of(pageIndex, size);
        Page<UserEntity> page = userRepository.buscarTodosUsuarios(nome, pageable);
        return page.map(mapper::mapToResponse);
    }
}
