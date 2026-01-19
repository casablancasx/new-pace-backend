package br.gov.agu.pace.escala.strategy;


import br.gov.agu.pace.domain.user.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class SetorStrategyFactory {

    private final AvaliadorSetorStrategy avaliadorSetorStrategy;

    private final PautistaSetorStrategy pautistaSetorStrategy;



    public SetorStrategy getStrategy(UserEntity user){

        return switch (user.getRole()) {
            case AVALIADOR -> avaliadorSetorStrategy;
            case PAUTISTA -> pautistaSetorStrategy;
            default -> null;
        };

    }
}
