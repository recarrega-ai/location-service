package ai.recarrega.locationservice.core.application;

import ai.recarrega.locationservice.core.domain.carregadores.Ponto;
import ai.recarrega.locationservice.core.domain.carregadores.dto.PontoDTO;
import ai.recarrega.locationservice.data.PontosDeRecargaRepository;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = { "/pontos" })
public class PontoController {
    private final GeometryFactory geo;
    private final PontosDeRecargaRepository pontosDeRecargaRepository;

    @Autowired
    public PontoController(PontosDeRecargaRepository pontosDeRecargaRepository) {
        this.pontosDeRecargaRepository = pontosDeRecargaRepository;
        geo = new GeometryFactory();
    }

    @GetMapping
    public ResponseEntity<List<PontoDTO>> pesquisaPontos() {
        List<PontoDTO> pontos = pontosDeRecargaRepository.findAll()
                .stream()
                .map(Ponto::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pontos);
    }

    @PostMapping
    public ResponseEntity<PontoDTO> criarPonto(@Validated @RequestBody PontoDTO pontoDTO) {
        Ponto ponto = this.pontosDeRecargaRepository.save(pontoDTO.toEntity(geo));
        return ResponseEntity.ok(ponto.toDTO());
    }
}
