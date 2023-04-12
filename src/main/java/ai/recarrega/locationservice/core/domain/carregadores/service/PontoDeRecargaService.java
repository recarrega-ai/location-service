package ai.recarrega.locationservice.core.domain.carregadores.service;

import ai.recarrega.locationservice.core.domain.carregadores.Ponto;
import ai.recarrega.locationservice.core.domain.carregadores.dto.PontoDTO;
import ai.recarrega.locationservice.infra.SpringGeometryFactory;
import ai.recarrega.locationservice.infra.data.PontosDeRecargaRepository;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
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

    private PontoDTO mapToDTO(Ponto ponto, boolean loadTomadas) {
        PontoDTO pontoDTO = ponto.toDTO(loadTomadas);
        pontoDTO.setTodasTomadas(pontosDeRecargaRepository.contarTodasAsTomadas(ponto));
        pontoDTO.setTomadasLivres(pontosDeRecargaRepository.contarTomadasLivres(ponto));
        return pontoDTO;
    }

    private PontoDTO mapToDTO(Ponto ponto) {
        return mapToDTO(ponto, false);
    }

    public List<PontoDTO> findAllFiltered(Double lat, Double lng, Double radius) {
        log.info("lat: {} lng: {} radius: {}", lat, lng, radius);
        Polygon shape = springGeometryFactory.createCircle(lat, lng, radius);
        return pontosDeRecargaRepository.findByCoordenadaIsWithin(shape)
            .stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }

    public PontoDTO createPoint(PontoDTO pontoDTO) {
        Point point = springGeometryFactory.fromCoordenada(pontoDTO.getCoordenada());
        log.info("Ponto criado em {} com {} tomadas", point, pontoDTO.getTomadas().size());
        Ponto ponto = pontoDTO.toEntity();
        ponto.setCoordenada(point);
        PontoDTO responseDto = mapToDTO(pontosDeRecargaRepository.save(ponto), true);
        log.info(
            "(tomadasLivres={}, todasTomadas={})",
            responseDto.getTomadasLivres(),
            responseDto.getTodasTomadas()
        );
        return responseDto;
    }

    public Optional<PontoDTO> findOne(long id) {
        return pontosDeRecargaRepository.findById(id)
            .map(i -> this.mapToDTO(i, false));
    }

    public Optional<PontoDTO> deletarPonto(Long id) {
        return pontosDeRecargaRepository.findById(id)
            .map(ponto -> {
                ponto.setDeletedAt(Calendar.getInstance());
                return pontosDeRecargaRepository.save(ponto);
            })
            .map(i -> this.mapToDTO(i, false));
    }
}
