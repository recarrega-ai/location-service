package ai.recarrega.locationservice.infra.encryption;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.stereotype.Component;

import ai.recarrega.locationservice.infra.encoders.HexEncoderProvider;

@Component
public class PBKDF2EncryptionProvider implements EncryptionProvider {
    private HexEncoderProvider encoderProvider;

    public PBKDF2EncryptionProvider(HexEncoderProvider encoderProvider) {
        this.encoderProvider = encoderProvider;
    }

    private KeySpec makeSpec(char[] data, byte[] salt) {
        return new PBEKeySpec(data, salt, 65536, 128);
    }

    @Override
    public String encrypt(String data) {
        try {
            byte[] salt = generateSalt();
            KeySpec keySpec = makeSpec(data.toCharArray(), salt);
            byte[] hash = getSecretKeyFactory().generateSecret(keySpec).getEncoded();
            return String.format(
                "%s:%s", 
                encoderProvider.toHex(hash), 
                encoderProvider.toHex(salt)
            );
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public SecretKeyFactory getSecretKeyFactory() {
        try {
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch(NoSuchAlgorithmException e) {
            return null;
        }
    }

    @Override
    public boolean verify(String inputPassword, String hashedPassword) {
        try {
            String[] parts = hashedPassword.split(":");

            byte[] hash = encoderProvider.fromHex(parts[0]);
            byte[] salt = encoderProvider.fromHex(parts[1]);

            KeySpec testHashKeySpec = makeSpec(inputPassword.toCharArray(), salt);
            byte[] testHash = getSecretKeyFactory().generateSecret(testHashKeySpec).getEncoded();

            int diff = hash.length ^ testHash.length;
            for(int i = 0; i < hash.length && i < testHash.length; i++)
                diff |= hash[i] ^ testHash[i];
            return diff == 0;
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            return false;
        }
    }
}
