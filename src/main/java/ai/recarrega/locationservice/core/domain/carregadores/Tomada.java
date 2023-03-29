package ai.recarrega.locationservice.core.domain.carregadores;

import ai.recarrega.locationservice.core.domain.carregadores.dto.TomadaDTO;
import ai.recarrega.locationservice.core.domain.carregadores.vo.StatusTomada;
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
    private Integer voltagem;

    @Column
    private Integer amperes;

    @ManyToOne
    private Ponto ponto;

    public Tomada(Integer voltagem, Integer amperes, Ponto ponto, StatusTomada status) {
        this(voltagem, amperes);
        this.ponto = ponto;
        this.status = status;
    }

    public Tomada(Integer voltagem, Integer amperes) {
        this.status = StatusTomada.LIVRE;
        this.amperes = amperes;
        this.voltagem = voltagem;
    }

    public TomadaDTO toDTO() {
        return TomadaDTO.builder()
                .id(id)
                .voltagem(voltagem)
                .amperes(amperes)
                .status(status)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tomada tomada)) return false;
        return Objects.equals(id, tomada.id) &&
                status == tomada.status &&
                Objects.equals(voltagem, tomada.voltagem) &&
                Objects.equals(amperes, tomada.amperes) &&
                Objects.equals(ponto, tomada.ponto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, voltagem, amperes, ponto);
    }
}
