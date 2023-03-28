package ai.recarrega.locationservice.core.domain.carregadores.dto;

import ai.recarrega.locationservice.core.domain.carregadores.Ponto;
import ai.recarrega.locationservice.core.domain.carregadores.Tomada;
import ai.recarrega.locationservice.core.domain.vo.StatusTomada;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TomadaDTO {
    private Long id;

    @NotNull
    @Min(110)
    private Integer voltagem;

    @NotNull
    @Min(20)
    private Integer amperes;

    @Builder.Default
    private StatusTomada status = StatusTomada.LIVRE;

    public Tomada toEntity() {
        return new Tomada(voltagem, amperes);
    }
}
