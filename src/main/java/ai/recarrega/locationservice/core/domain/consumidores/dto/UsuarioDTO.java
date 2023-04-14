package ai.recarrega.locationservice.core.domain.consumidores.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class UsuarioDTO {
    private String nome;
    private String email;
    private String senha;
}
