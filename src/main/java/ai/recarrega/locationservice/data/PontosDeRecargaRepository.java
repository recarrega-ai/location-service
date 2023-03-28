package ai.recarrega.locationservice.data;

import ai.recarrega.locationservice.core.domain.carregadores.Ponto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PontosDeRecargaRepository extends JpaRepository<Ponto, Long> {
}
