package ai.recarrega.locationservice.infra.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ai.recarrega.locationservice.core.domain.consumidores.Usuario;
import jakarta.validation.constraints.Email;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(@Email String email);
}
