package br.gov.agu.pace.domain.user.pautista;

import br.gov.agu.pace.commons.exceptions.ResourceNotFoundException;
import br.gov.agu.pace.domain.pauta.repository.PautaRepository;
import br.gov.agu.pace.domain.user.UserEntity;
import br.gov.agu.pace.domain.user.UserRepository;
import br.gov.agu.pace.domain.user.UserService;
import br.gov.agu.pace.domain.user.dto.PautistaResponseDTO;
import br.gov.agu.pace.domain.user.dto.UsuarioSapiensDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import static br.gov.agu.pace.domain.enums.UserRole.PAUTISTA;

@Service
@RequiredArgsConstructor
public class PautistaService {

    private final UserRepository repository;
    private final UserService userService;
    private final PautistaMapper mapper;
    private final PautaRepository pautaRepository;

    public Page<PautistaResponseDTO> listarPautistas(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserEntity> avaliadoresPage = repository.findAllByRole(PAUTISTA, pageable);
        return avaliadoresPage.map(mapper::mapToResponse);
    }

    public PautistaResponseDTO cadastrarPautista(UsuarioSapiensDTO dto, String token){
        var pautista = userService.cadastrarUsuario(dto, token, PAUTISTA);
        return mapper.mapToResponse(pautista);
    }

    public UserEntity selecionarPautista(List<UserEntity> pautistas, LocalDate dataPauta){
        // Busca os IDs dos pautistas que já estão escalados na data
        List<Long> pautistasEscaladosIds = pautaRepository.buscarPautistasEscaladosNaData(dataPauta);

        // Filtra os pautistas que não possuem pauta na data especificada
        return pautistas.stream()
                .filter(p -> !pautistasEscaladosIds.contains(p.getSapiensId()))
                .min(Comparator.comparingLong(UserEntity::calcularCargaTrabalho))
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum pautista disponível para a data " + dataPauta + ". Todos já possuem pauta nesta data."));
    }

    //Busca avaliadores por ids ou retorna todos se nenhum id for informado
    public List<UserEntity> buscarPautistasPorIds(List<Long> ids){
        if (ids.isEmpty()) return repository.findAllByRole(PAUTISTA);
        return repository.findAllByPorIdAndRole(ids, PAUTISTA);
    }
}
