package ai.recarrega.locationservice.application.controller.dto;

import ai.recarrega.locationservice.domain.consumidores.Usuario;
import ai.recarrega.locationservice.infra.validation.Unique;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class UsuarioDTO {
    @NotNull
    @NotEmpty
    @Size(min=3, max=255)
    private String nome;

    @Email
    @NotNull
    @NotEmpty
    @Size(max=512)
    @Unique(entity = Usuario.class, field = "email")
    private String email;

    @NotNull
    @NotEmpty
    @Size(min=8)
    private String senha;
}
