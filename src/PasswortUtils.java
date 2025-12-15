import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;


public class PasswortUtils {
    public static String hashPasswort(String passwort) throws NoSuchAlgorithmException {

        //Algorithmus SHA-256
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        //Passwort in Bytes und haschen
        byte[] encodedhash = digest.digest(passwort.getBytes(StandardCharsets.UTF_8));
        //Byte in Text umwandeln
        return bytesToHex(encodedhash);
    }
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
