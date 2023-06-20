package ai.recarrega.locationservice.infra.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ai.recarrega.locationservice.domain.carregadores.Ponto;
import ai.recarrega.locationservice.domain.carregadores.Tomada;
import ai.recarrega.locationservice.domain.carregadores.vo.StatusTomada;

@Repository
public interface TomadasRepository extends JpaRepository<Tomada, Long> {
	List<Tomada> findByStatusAndPonto(StatusTomada status, Ponto ponto);
}
