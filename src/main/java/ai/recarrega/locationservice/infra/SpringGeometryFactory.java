package ai.recarrega.locationservice.infra;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.util.GeometricShapeFactory;
import org.springframework.stereotype.Component;

import ai.recarrega.locationservice.domain.carregadores.vo.Coordenada;

@Component
public class SpringGeometryFactory {
    public Polygon createCircle(double x, double y, double radius) {
        GeometricShapeFactory shapeFactory = new GeometricShapeFactory();
        shapeFactory.setNumPoints(32);
        shapeFactory.setCentre(new Coordinate(x, y));
        shapeFactory.setSize(radius * 2);
        return shapeFactory.createCircle();
    }

    public Point fromCoordenada(Coordenada coordenada) {
        GeometryFactory geoFactory = new GeometryFactory();
        return geoFactory.createPoint(coordenada.toCoord());
    }
}
