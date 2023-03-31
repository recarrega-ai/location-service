package ai.recarrega.locationservice.core.domain.carregadores.dto;

import ai.recarrega.locationservice.core.domain.carregadores.Ponto;
import ai.recarrega.locationservice.core.domain.carregadores.Tomada;
import ai.recarrega.locationservice.core.domain.carregadores.vo.Coordenada;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
public class PontoDTO {
    private Long id;

    @Builder.Default
    private Set<TomadaDTO> tomadas = new HashSet<>();

    @NotEmpty
    @NotBlank
    private String nome;
    private Coordenada coordenada;

    private Integer todasTomadas;
    private Integer tomadasLivres;

    public Ponto toEntity() {
        Ponto ponto = new Ponto();
        ponto.setNome(nome);
        if(tomadas == null) {
            tomadas = new HashSet<>();
        }
        Set<Tomada> tomadasEntity = tomadas.stream()
                .map(tomada -> tomada.toEntity(ponto))
                .collect(Collectors.toSet());
        ponto.setTomadas(tomadasEntity);
        return ponto;
    }
}
