package ai.recarrega.locationservice.core.application;

import ai.recarrega.locationservice.core.domain.carregadores.Ponto;
import ai.recarrega.locationservice.core.domain.carregadores.dto.PontoDTO;
import ai.recarrega.locationservice.data.PontosDeRecargaRepository;
import ai.recarrega.locationservice.data.dto.SpringGeometryFactory;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = { "/pontos" })
public class PontoController {
    private final PontosDeRecargaRepository pontosDeRecargaRepository;
    private final SpringGeometryFactory springGeometryFactory;

    @Autowired
    public PontoController(
            SpringGeometryFactory springGeometryFactory,
            PontosDeRecargaRepository pontosDeRecargaRepository) {
        this.pontosDeRecargaRepository = pontosDeRecargaRepository;
        this.springGeometryFactory = springGeometryFactory;
    }

    @GetMapping
    public ResponseEntity<List<PontoDTO>> pesquisaPontos(
        @RequestParam(required = false, defaultValue = "0.0")
        Double lat,
        @RequestParam(required = false, defaultValue = "0.0")
        Double lng,
        @RequestParam(required = false, defaultValue = "3.0")
        Double radius
    ) {
        log.info("lat: {} lng: {} radius: {}", lat, lng, radius);
        Polygon shape = springGeometryFactory.createCircle(lat, lng, radius);
        log.info("Polygon generated: {}", shape.toString());
        List<PontoDTO> pontos = pontosDeRecargaRepository.findByCoordenadaIsWithin(shape)
                .stream()
                .map(i -> {
                    PontoDTO pontoDTO = i.toDTO(false);
                    pontoDTO.setTodasTomadas(pontosDeRecargaRepository.contarTodasAsTomadas(i));
                    pontoDTO.setTomadasLivres(pontosDeRecargaRepository.contarTomadasLivres(i));
                    return pontoDTO;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(pontos);
    }

    @PostMapping
    public ResponseEntity<PontoDTO> criarPonto(@Validated @RequestBody PontoDTO pontoDTO) {
        Point point = springGeometryFactory.fromCoordenada(pontoDTO.getCoordenada());
        log.info("Ponto criado em {} com {} tomadas", point, pontoDTO.getTomadas().size());
        Ponto ponto = pontoDTO.toEntity();
        ponto.setCoordenada(point);
        ponto = pontosDeRecargaRepository.save(ponto);
        PontoDTO responseDto = ponto.toDTO(true);
        responseDto.setTomadasLivres(pontosDeRecargaRepository.contarTomadasLivres(ponto));
        responseDto.setTodasTomadas(pontosDeRecargaRepository.contarTodasAsTomadas(ponto));
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PontoDTO> pegarPonto(@PathVariable("id") Long id) {
        return pontosDeRecargaRepository.findById(id)
                .map(i -> {
                    PontoDTO pontoDTO = i.toDTO(true);
                    pontoDTO.setTodasTomadas(pontosDeRecargaRepository.contarTodasAsTomadas(i));
                    pontoDTO.setTomadasLivres(pontosDeRecargaRepository.contarTomadasLivres(i));
                    return pontoDTO;
                })
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
