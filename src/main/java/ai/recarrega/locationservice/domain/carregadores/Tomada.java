package ai.recarrega.locationservice.domain.carregadores;

import ai.recarrega.locationservice.application.controller.dto.TomadaDTO;
import ai.recarrega.locationservice.domain.carregadores.vo.StatusTomada;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tomadas")
public class Tomada {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private StatusTomada status;

    @Column
    private Double kWh;

    @ManyToOne
    private Ponto ponto;

    public Tomada(Double kWh, Ponto ponto, StatusTomada status) {
        this(kWh);
        this.ponto = ponto;
        this.status = status;
    }

    public Tomada(Double kWh) {
        this.status = StatusTomada.LIVRE;
        this.kWh = kWh;
    }

    public TomadaDTO toDTO() {
        return TomadaDTO.builder()
                .id(id)
                .kWh(kWh)
                .status(status)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tomada tomada)) return false;
        return Objects.equals(id, tomada.id) &&
                status == tomada.status &&
                Objects.equals(kWh, tomada.kWh) &&
                Objects.equals(ponto, tomada.ponto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, kWh, ponto);
    }
}
