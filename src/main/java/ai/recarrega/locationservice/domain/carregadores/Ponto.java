package ai.recarrega.locationservice.domain.carregadores;

import ai.recarrega.locationservice.application.controller.dto.PontoDTO;
import ai.recarrega.locationservice.domain.carregadores.vo.Coordenada;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.util.*;
import java.util.stream.Collectors;

@Data
@Entity
@NoArgsConstructor
@Table(name = "pontos")
public class Ponto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column()
    private String nome;

    @Column()
    private Point coordenada;

    @Column(nullable = true)
    private Calendar deletedAt;

    @OneToMany(
        fetch = FetchType.LAZY,
        targetEntity = Tomada.class,
        cascade = CascadeType.ALL
    )
    private Set<Tomada> tomadas = new HashSet<>();

    public PontoDTO toDTO(boolean loadTomadas) {
        PontoDTO.PontoDTOBuilder builder = PontoDTO.builder()
                .coordenada(Coordenada.fromPoint(coordenada))
                .deletedAt(deletedAt)
                .nome(nome)
                .id(id);
        if(loadTomadas) {
            builder.tomadas(
                tomadas.stream()
                    .map(Tomada::toDTO)
                    .collect(Collectors.toSet())
            );
        }
        return builder.build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ponto ponto = (Ponto) o;
        return Objects.equals(id, ponto.id) &&
                Objects.equals(nome, ponto.nome) &&
                Objects.equals(coordenada, ponto.coordenada);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, coordenada);
    }
}
