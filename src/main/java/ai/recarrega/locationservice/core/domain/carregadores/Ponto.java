package ai.recarrega.locationservice.core.domain.carregadores;

import ai.recarrega.locationservice.core.domain.carregadores.dto.PontoDTO;
import ai.recarrega.locationservice.core.domain.vo.Coordenada;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.util.HashSet;
import java.util.Set;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ponto", cascade = { CascadeType.ALL })
    private Set<Tomada> tomadas = new HashSet<>();

    public PontoDTO toDTO() {
        return PontoDTO.builder()
                .coordenada(Coordenada.fromPoint(coordenada))
                .nome(nome)
                .tomadas(tomadas.stream().map(Tomada::toDTO).collect(Collectors.toSet()))
                .id(id)
                .build();
    }
}
