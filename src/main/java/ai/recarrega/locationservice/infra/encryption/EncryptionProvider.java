package ai.recarrega.locationservice.infra.encryption;

import java.security.SecureRandom;

import javax.crypto.SecretKeyFactory;

public interface EncryptionProvider {
    SecretKeyFactory getSecretKeyFactory();
    String encrypt(String data);
    boolean verify(String inputPassword, String hashedPassword);
    default byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
}
