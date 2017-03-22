import sun.misc.BASE64Encoder;
import javax.crypto.Cipher;
import java.security.*;

public class SecuritySigning {

    public static void main(String[] args) throws Exception {

        String message = "This is the message being signed";

        double iTime, mTime, fTime;
        //= System.nanoTime();

        iTime = System.nanoTime();

        // Declare new key generator and define key size
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024); // This defines the key size

        // Generate a new key
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();

        // Generate a new signature
        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initSign(privateKey);
        signature.update((message).getBytes());
        byte[] sign = signature.sign();

        // Digest
        MessageDigest sha1 = MessageDigest.getInstance("SHA1");
        byte[] d = sha1.digest((message).getBytes());

        // Encrypt
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] c = cipher.doFinal(d);

        mTime = System.nanoTime();

        fTime = mTime - iTime;

        // Display results
        System.out.println("m: " + message);
        System.out.println("d: " + byteArray2Hex(d));
        System.out.println("e: " + byteArray2Hex(c));
        System.out.println("sign: " + byteArray2Hex(sign));
        System.out.println("elapsed time: " + fTime);

    }

    private static final char[] hex = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static String byteArray2Hex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (final byte b : bytes) {
            sb.append(hex[(b & 0xF0) >> 4]);
            sb.append(hex[b & 0x0F]);
        }
        return sb.toString();
    }
}


