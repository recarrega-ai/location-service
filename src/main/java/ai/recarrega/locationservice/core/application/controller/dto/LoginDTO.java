package ai.recarrega.locationservice.core.application.controller.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class LoginDTO {
    @Email
    public String email;
    public String senha;
}
