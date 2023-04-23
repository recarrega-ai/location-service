package ai.recarrega.locationservice.application.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;

import ai.recarrega.locationservice.domain.carregadores.Ponto;
import ai.recarrega.locationservice.domain.carregadores.Tomada;
import ai.recarrega.locationservice.domain.carregadores.vo.Coordenada;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
    private Calendar deletedAt;

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
