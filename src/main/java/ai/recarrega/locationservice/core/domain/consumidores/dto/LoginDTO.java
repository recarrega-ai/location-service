package ai.recarrega.locationservice.core.domain.consumidores.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class LoginDTO {
    @Email
    public String email;
    public String senha;
}
