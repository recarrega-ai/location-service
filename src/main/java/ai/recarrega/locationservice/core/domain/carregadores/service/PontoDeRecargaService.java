package ai.recarrega.locationservice.core.domain.carregadores.service;

import ai.recarrega.locationservice.core.domain.carregadores.Ponto;
import ai.recarrega.locationservice.core.domain.carregadores.dto.PontoDTO;
import ai.recarrega.locationservice.data.PontosDeRecargaRepository;
import ai.recarrega.locationservice.data.dto.SpringGeometryFactory;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PontoDeRecargaService {
    private final PontosDeRecargaRepository pontosDeRecargaRepository;
    private final SpringGeometryFactory springGeometryFactory;

    @Autowired
    public PontoDeRecargaService(
            SpringGeometryFactory springGeometryFactory,
            PontosDeRecargaRepository pontosDeRecargaRepository) {
        this.pontosDeRecargaRepository = pontosDeRecargaRepository;
        this.springGeometryFactory = springGeometryFactory;
    }

    public List<PontoDTO> findAllFiltered(Double lat, Double lng, Double radius) {
        log.info("lat: {} lng: {} radius: {}", lat, lng, radius);
        Polygon shape = springGeometryFactory.createCircle(lat, lng, radius);
        log.info("Polygon generated: {}", shape.toString());
        return pontosDeRecargaRepository.findByCoordenadaIsWithin(shape)
            .stream()
            .map(i -> {
                PontoDTO pontoDTO = i.toDTO(false);
                pontoDTO.setTodasTomadas(pontosDeRecargaRepository.contarTodasAsTomadas(i));
                pontoDTO.setTomadasLivres(pontosDeRecargaRepository.contarTomadasLivres(i));
                return pontoDTO;
            })
            .collect(Collectors.toList());
    }

    public PontoDTO createPoint(PontoDTO pontoDTO) {
        Point point = springGeometryFactory.fromCoordenada(pontoDTO.getCoordenada());
        log.info("Ponto criado em {} com {} tomadas", point, pontoDTO.getTomadas().size());
        Ponto ponto = pontoDTO.toEntity();
        ponto.setCoordenada(point);
        PontoDTO responseDto = pontosDeRecargaRepository.save(ponto).toDTO(true);
        responseDto.setTomadasLivres(pontosDeRecargaRepository.contarTomadasLivres(ponto));
        responseDto.setTodasTomadas(pontosDeRecargaRepository.contarTodasAsTomadas(ponto));
        log.info(
            "(tomadasLivres={}, todasTomadas={})",
            responseDto.getTomadasLivres(),
            responseDto.getTodasTomadas()
        );
        return responseDto;
    }

    public Optional<PontoDTO> findOne(long id) {
        return pontosDeRecargaRepository.findById(id)
            .map(i -> {
                PontoDTO pontoDTO = i.toDTO(true);
                pontoDTO.setTodasTomadas(pontosDeRecargaRepository.contarTodasAsTomadas(i));
                pontoDTO.setTomadasLivres(pontosDeRecargaRepository.contarTomadasLivres(i));
                return pontoDTO;
            });
    }
}
