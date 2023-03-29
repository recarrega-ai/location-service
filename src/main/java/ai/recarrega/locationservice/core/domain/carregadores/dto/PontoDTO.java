package ai.recarrega.locationservice.core.domain.carregadores.dto;

import ai.recarrega.locationservice.core.domain.carregadores.Ponto;
import ai.recarrega.locationservice.core.domain.carregadores.Tomada;
import ai.recarrega.locationservice.core.domain.carregadores.vo.Coordenada;
import ai.recarrega.locationservice.core.domain.carregadores.vo.StatusTomada;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Data
@Builder
public class PontoDTO {
    private Long id;

    @Builder.Default
    private Set<TomadaDTO> tomadas = new HashSet<>();

    @NotEmpty
    @NotBlank
    private String nome;
    private Coordenada coordenada;

    public Long getNumeroTomadas() {
        if(tomadas == null) return 0L;
        return (long) tomadas.size();
    }

    public Long getTomadasLivres() {
        if(tomadas == null) return 0L;
        Predicate<TomadaDTO> estaLivre = i -> i.getStatus().equals(StatusTomada.LIVRE);
        return tomadas.stream().filter(estaLivre).count();
    }

    public Ponto toEntity(GeometryFactory geoFactory) {
        Ponto ponto = new Ponto();
        Point point = geoFactory.createPoint(coordenada.toCoord());
        ponto.setNome(nome);
        if(tomadas == null) {
            tomadas = new HashSet<>();
        }
        Set<Tomada> tomadasEntity = tomadas.stream()
                .map(tomada -> tomada.toEntity(ponto))
                .collect(Collectors.toSet());
        ponto.setTomadas(tomadasEntity);
        ponto.setCoordenada(point);
        return ponto;
    }
}
