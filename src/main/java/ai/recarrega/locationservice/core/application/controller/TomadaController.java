package ai.recarrega.locationservice.core.application.controller;

import ai.recarrega.locationservice.core.domain.carregadores.dto.TomadaDTO;
import ai.recarrega.locationservice.core.domain.carregadores.vo.StatusTomada;
import ai.recarrega.locationservice.infra.data.TomadasRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/tomadas")
public class TomadaController {
    private final TomadasRepository tomadasRepository;

    @Autowired
    public TomadaController(TomadasRepository tomadasRepository) {
        this.tomadasRepository = tomadasRepository;
    }

    @PatchMapping("/{tomadaId}/{status}")
    public ResponseEntity<TomadaDTO> atualizarStatus(
        @PathVariable("tomadaId") Long tomadaId,
        @PathVariable("status") StatusTomada status
    ) {
        return tomadasRepository.findById(tomadaId).map(tomada -> {
            tomada.setStatus(status);
            return ResponseEntity.ok(tomadasRepository.save(tomada).toDTO());
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
