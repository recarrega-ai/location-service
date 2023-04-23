package ai.recarrega.locationservice.core.domain.consumidores.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.recarrega.locationservice.core.application.controller.dto.LoginDTO;
import ai.recarrega.locationservice.core.application.controller.dto.UsuarioDTO;
import ai.recarrega.locationservice.core.domain.consumidores.Usuario;
import ai.recarrega.locationservice.infra.data.UserRepository;
import ai.recarrega.locationservice.infra.encryption.EncryptionProvider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsuarioService {
    private final EncryptionProvider encryptionProvider;
    private final UserRepository userRepository;

    @Autowired
    public UsuarioService(
        UserRepository userRepository,
        EncryptionProvider encryptionProvider
    ) {
        this.encryptionProvider = encryptionProvider;
        this.userRepository = userRepository;
    }

    public Usuario registrar(UsuarioDTO usuarioDTO) {
        log.info(usuarioDTO.toString());
        String byteSenha = encryptionProvider.encrypt(usuarioDTO.getSenha());
        Usuario usuario = Usuario.builder()
            .email(usuarioDTO.getEmail())
            .nome(usuarioDTO.getNome())
            .senha(byteSenha)
            .build();
        return userRepository.save(usuario);
    }

    public String verifyPasswordTest(LoginDTO loginDTO) {
        Optional<Usuario> usuario = userRepository.findByEmail(loginDTO.email);
        if(usuario.isEmpty()) {
            throw new RuntimeException("Ususario nao encontrado");
        }
        boolean isValid = encryptionProvider.verify(
            loginDTO.senha,
            usuario.get().getSenha()
        );
        return  ? "verdade" : "falso";
    }
}
