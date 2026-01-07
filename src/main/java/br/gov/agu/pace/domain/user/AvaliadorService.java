package br.gov.agu.pace.domain.user;

import br.gov.agu.pace.commons.exceptions.ResourceNotFoundException;
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

    public Page<UserEntity> listarAvaliadores(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAllByRole(AVALIADOR, pageable);
    }

    public UserEntity cadastrarAvaliador(UsuarioSapiensDTO dto, String token){
        return userService.cadastrarUsuario(dto, token, AVALIADOR);
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
