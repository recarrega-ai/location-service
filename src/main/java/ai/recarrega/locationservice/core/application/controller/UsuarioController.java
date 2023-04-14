package ai.recarrega.locationservice.core.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.recarrega.locationservice.core.domain.consumidores.dto.LoginDTO;
import ai.recarrega.locationservice.core.domain.consumidores.dto.UsuarioDTO;
import ai.recarrega.locationservice.core.domain.consumidores.service.UsuarioService;

@RestController
@RequestMapping(path = "/usuarios")
public class UsuarioController {
    private UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping()
    public String registrarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return this.usuarioService.registrar(usuarioDTO).getSenha();
    }

    @PostMapping("/login")
    public String loginUsuario(@RequestBody LoginDTO loginDTO) {
        return this.usuarioService.verifyPasswordTest(loginDTO);
    }
}
