package ai.recarrega.locationservice.core.application;

import ai.recarrega.locationservice.core.domain.carregadores.dto.TomadaDTO;
import ai.recarrega.locationservice.core.domain.carregadores.vo.StatusTomada;
import ai.recarrega.locationservice.data.TomadasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        @PathVariable("status") String statusTomada
    ) {
        StatusTomada status = StatusTomada.valueOf(statusTomada);
        return tomadasRepository.findById(tomadaId).map(tomada -> {
            tomada.setStatus(status);
            return ResponseEntity.ok(tomadasRepository.save(tomada).toDTO());
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
