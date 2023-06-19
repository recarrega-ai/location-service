package ai.recarrega.locationservice.infra.data;

import org.locationtech.jts.geom.Polygon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ai.recarrega.locationservice.domain.carregadores.Ponto;

import java.util.List;

@Repository
public interface PontosDeRecargaRepository extends JpaRepository<Ponto, Long> {
    @Query("SELECT p FROM Ponto p WHERE within(p.coordenada, :circle) = true AND p.deletedAt is null")
    List<Ponto> findByCoordenadaIsWithin(Polygon circle);

    @Query("SELECT COUNT(t.id) FROM Tomada t WHERE t.ponto = :ponto AND t.status = " +
		"ai.recarrega.locationservice.domain.carregadores.vo.StatusTomada.LIVRE")
    Integer contarTomadasLivres(Ponto ponto);

    @Query("SELECT COUNT(t.id) FROM Tomada t WHERE t.ponto = :ponto")
    Integer contarTodasAsTomadas(Ponto ponto);
}
