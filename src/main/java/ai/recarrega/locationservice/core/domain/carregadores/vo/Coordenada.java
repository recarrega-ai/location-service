package ai.recarrega.locationservice.core.domain.carregadores.vo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Point;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coordenada {
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;

    public static Coordenada fromPoint(Point point) {
        return new Coordenada(point.getX(), point.getY());
    }

    public Coordinate toCoord() {
        return new Coordinate(latitude, longitude);
    }
}
