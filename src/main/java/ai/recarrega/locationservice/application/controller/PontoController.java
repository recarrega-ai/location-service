package ai.recarrega.locationservice.application.controller;

import ai.recarrega.locationservice.application.controller.dto.PontoDTO;
import ai.recarrega.locationservice.domain.carregadores.service.PontoDeRecargaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = { "/pontos" })
public class PontoController {
    private final PontoDeRecargaService pontoDeRecargaService;

    @Autowired
    public PontoController(PontoDeRecargaService pontoDeRecargaService) {
        this.pontoDeRecargaService = pontoDeRecargaService;
    }

    @GetMapping
    public ResponseEntity<List<PontoDTO>> pesquisaPontos(
        @RequestParam(required = false, defaultValue = "0.0") Double lat,
        @RequestParam(required = false, defaultValue = "0.0") Double lng,
        @RequestParam(required = false, defaultValue = "25.0") Double radius
    ) {
        log.info("Searching for points into a range of {}km", radius);
        return ResponseEntity.ok(pontoDeRecargaService.findAllFiltered(lat, lng, radius));
    }

    @PostMapping
    public ResponseEntity<PontoDTO> criarPonto(@Validated @RequestBody PontoDTO pontoDTO) {
		log.info("Creating new charging point");
        return ResponseEntity.ok(pontoDeRecargaService.createPoint(pontoDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PontoDTO> pegarPonto(@PathVariable("id") Long id) {
		log.info("pointId={}", id);
        return pontoDeRecargaService.findOne(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarPonto(@PathVariable("id") Long id) {
        return ResponseEntity.accepted().body(pontoDeRecargaService.deletarPonto(id));
    }
}
