package ai.recarrega.locationservice.application.controller.dto;

import ai.recarrega.locationservice.domain.carregadores.Ponto;
import ai.recarrega.locationservice.domain.carregadores.Tomada;
import ai.recarrega.locationservice.domain.carregadores.vo.StatusTomada;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TomadaDTO {
    private Long id;

    @NotNull @Min(0) @Max(200)
    private Double kWh;

    @Builder.Default
    private StatusTomada status = StatusTomada.LIVRE;

    public Tomada toEntity(Ponto ponto) {
		if(status == null) {
			status = StatusTomada.LIVRE;
		}
        return new Tomada(kWh, ponto, status);
    }
}
