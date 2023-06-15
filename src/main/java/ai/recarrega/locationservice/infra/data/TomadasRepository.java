package ai.recarrega.locationservice.infra.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ai.recarrega.locationservice.domain.carregadores.Tomada;

@Repository
public interface TomadasRepository extends JpaRepository<Tomada, Long> {
}
