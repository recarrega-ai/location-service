package ai.recarrega.locationservice.application.controller;

import ai.recarrega.locationservice.application.controller.dto.TomadaDTO;
import ai.recarrega.locationservice.domain.carregadores.vo.StatusTomada;
import ai.recarrega.locationservice.infra.data.PontosDeRecargaRepository;
import ai.recarrega.locationservice.infra.data.TomadasRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/tomadas")
public class TomadaController {
    private final TomadasRepository tomadasRepository;
	private final PontosDeRecargaRepository pontosRepository;

    @Autowired
    public TomadaController(
		TomadasRepository tomadasRepository,
		PontosDeRecargaRepository pontosRepository
	) {
        this.tomadasRepository = tomadasRepository;
		this.pontosRepository = pontosRepository;
    }

    @PatchMapping("/{pontoId}/em_uso")
    public ResponseEntity<?> ocuparTomada(@PathVariable("pontoId") Long pontoId) {
		var ponto = pontosRepository.findById(pontoId);
		if(ponto.isEmpty()) {
			return ResponseEntity.badRequest()
				.body("Ponto não encontrado!");
		}
        return tomadasRepository.findByStatusAndPonto(StatusTomada.LIVRE, ponto.get())
			.stream().findFirst()
			.map(tomada -> {
				log.info("Tomada: {}", tomada.getId());
				tomada.setStatus(StatusTomada.EM_USO);
				tomadasRepository.save(tomada);
				return ResponseEntity.ok(tomada.toDTO());
			})
			.orElse(ResponseEntity.notFound().build());
    }
}
