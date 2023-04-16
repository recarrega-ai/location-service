package ai.recarrega.locationservice.infra.encoders;

import org.springframework.stereotype.Component;

@Component
public class HexEncoderProvider {
    public String toHex(byte[] array)
    {
        StringBuilder sb = new StringBuilder();
        for (byte b : array) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    public byte[] fromHex(String hexData) {
        byte[] binary = new byte[hexData.length() / 2];
        for(int i = 0; i < binary.length; i++)
        {
            binary[i] = (byte)Integer.parseInt(hexData.substring(2*i, 2*i+2), 16);
        }
        return binary;
    }
}
