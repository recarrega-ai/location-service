package ai.recarrega.locationservice.core.application;

import ai.recarrega.locationservice.core.domain.carregadores.Ponto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = { "/pontos" })
public class PontoController {
    @GetMapping
    public ResponseEntity<List<Ponto>> pesquisaPontos() {
        return ResponseEntity.ok(new ArrayList<>());
    }
}
