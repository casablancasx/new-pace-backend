package br.gov.agu.pace.domain.user;

import br.gov.agu.pace.domain.enums.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.TimeZone;

@Entity
@Table(name = "tb_users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class UserEntity extends SapiensUser{
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private LocalDateTime ultimoAcesso;
    private LocalDateTime dataCadastro = LocalDateTime.now(TimeZone.getDefault().toZoneId());
}
