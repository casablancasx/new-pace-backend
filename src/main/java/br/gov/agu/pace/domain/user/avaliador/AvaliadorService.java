package br.gov.agu.pace.domain.user.avaliador;

import br.gov.agu.pace.commons.exceptions.ResourceNotFoundException;
import br.gov.agu.pace.domain.user.UserEntity;
import br.gov.agu.pace.domain.user.UserRepository;
import br.gov.agu.pace.domain.user.UserService;
import br.gov.agu.pace.domain.user.dto.AvaliadorResponseDTO;
import br.gov.agu.pace.domain.user.dto.UsuarioSapiensDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

import static br.gov.agu.pace.domain.enums.UserRole.AVALIADOR;

@Service
@RequiredArgsConstructor
public class AvaliadorService {

    private final UserRepository repository;
    private final UserService userService;
    private final AvaliadorMapper mapper;

    public Page<AvaliadorResponseDTO> listarAvaliadores(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserEntity> avaliadoresPage = repository.findAllByRole(AVALIADOR, pageable);
        return avaliadoresPage.map(mapper::mapToResponse);
    }

    public AvaliadorResponseDTO cadastrarAvaliador(UsuarioSapiensDTO dto, String token){

        var avaliador = userService.cadastrarUsuario(dto, token, AVALIADOR);
        return mapper.mapToResponse(avaliador);
    }

    public UserEntity selecionarAvaliador(List<UserEntity> avaliadores){
        return avaliadores.stream()
                .min(Comparator.comparingLong(UserEntity::calcularCargaTrabalho))
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum avaliador disponível para seleção."));
    }

    //Busca avaliadores por ids ou retorna todos se nenhum id for informado
    public List<UserEntity> buscarAvaliadoresPorIds(List<Long> ids){
        if (ids.isEmpty()) return repository.findAllByRole(AVALIADOR);
        return repository.findAllByPorIdAndRole(ids, AVALIADOR);
    }
}
