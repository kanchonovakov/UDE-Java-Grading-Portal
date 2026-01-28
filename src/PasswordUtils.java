import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;


public class PasswordUtils {
    public static String hashPassword(String password) throws NoSuchAlgorithmException {

        // SHA-256 Algorithm
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        // Convert password to bytes and hash it
        byte[] encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        // Convert bytes to hex string
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