package br.gov.agu.pace.domain.user;

import br.gov.agu.pace.domain.enums.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    List<UserEntity> findAllByRole(UserRole role);

    Page<UserEntity> findAllByRole(UserRole role, Pageable pageable);

    @Query("SELECT u FROM UserEntity u WHERE u.sapiensId IN :ids AND u.role = :role")
    List<UserEntity> findAllByPorIdAndRole(List<Long> ids, UserRole role);

    @Query("SELECT u FROM UserEntity u WHERE (:nome IS NULL OR LOWER(u.nome) LIKE LOWER(CONCAT('%', :nome, '%')))")
    Page<UserEntity> buscarTodosUsuarios(String nome, Pageable pageable);
}
