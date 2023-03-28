package ai.recarrega.locationservice.core.domain.carregadores;

import ai.recarrega.locationservice.core.domain.carregadores.dto.TomadaDTO;
import ai.recarrega.locationservice.core.domain.vo.StatusTomada;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "tomadas")
@NoArgsConstructor
@AllArgsConstructor
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

    @ManyToOne(fetch = FetchType.EAGER)
    private Ponto ponto;

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
}
