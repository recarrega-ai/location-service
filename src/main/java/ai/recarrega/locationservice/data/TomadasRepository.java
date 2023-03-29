package ai.recarrega.locationservice.data;

import ai.recarrega.locationservice.core.domain.carregadores.Tomada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TomadasRepository extends JpaRepository<Tomada, Long> {
}
