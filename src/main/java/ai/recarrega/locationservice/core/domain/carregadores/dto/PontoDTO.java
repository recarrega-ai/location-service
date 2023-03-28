package ai.recarrega.locationservice.core.domain.carregadores.dto;

import ai.recarrega.locationservice.core.domain.carregadores.Ponto;
import ai.recarrega.locationservice.core.domain.vo.Coordenada;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
public class PontoDTO {
    @Nullable
    private Long id;

    @Nullable
    @Builder.Default
    private Set<TomadaDTO> tomadas = new HashSet<>();

    @NotEmpty
    @NotBlank
    private String nome;
    private Coordenada coordenada;

    public Integer getNumeroTomadas() {
        if(tomadas == null) return 0;
        return tomadas.size();
    }

    public Ponto toEntity(GeometryFactory geoFactory) {
        Ponto ponto = new Ponto();
        Point point = geoFactory.createPoint(coordenada.toCoord());
        ponto.setNome(nome);
        if(tomadas == null) {
            tomadas = new HashSet<>();
        }
        ponto.setTomadas(tomadas.stream().map(TomadaDTO::toEntity).collect(Collectors.toSet()));
        ponto.setCoordenada(point);
        return ponto;
    }
}
