import java.io.*;
import java.security.*;

public class RSAsign {

	public static void main(String[] args) {
		if (args.length != 1) {
			// No tiene los argumentos correctos para el programa
			System.out.println("Uso: RSAsign <archivo>");
		} else {
			try {
				// Guarda tiempo inicial
				double iTime = System.nanoTime();

				// Inicializa instancia del generador con DSA y del generador random
				KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
				SecureRandom random = SecureRandom.getInstance("SHA1PRNG");

				// Inicializa el generador de llaves con tamaño definido de llave y una fuente de aleatoriedad
				keyGenerator.initialize(1024, random);

				// Genera llaves
				KeyPair pair = keyGenerator.generateKeyPair();
				PrivateKey priv = pair.getPrivate();
				PublicKey pub = pair.getPublic();

				// Inicializa objecto de firma con la llave privada
				Signature rsa = Signature.getInstance("SHA1withRSA");
				rsa.initSign(priv);

				// Lee el archivo a firmar
				FileInputStream fileInput = new FileInputStream(args[0]);
				BufferedInputStream bufferInput = new BufferedInputStream(fileInput);
				byte[] buffer = new byte[1024];
				int len;
				while (bufferInput.available() != 0) {
					len = bufferInput.read(buffer);
					rsa.update(buffer, 0, len);
				}
				bufferInput.close();

				// Generar firma del archivo leido
				byte[] realSignature = rsa.sign();

				// Guarda tiempo final
				double fTime = System.nanoTime();

				// Guarda la firma en archivo
				FileOutputStream signFileOutput = new FileOutputStream("RSAs.txt");
				signFileOutput.write(realSignature);
				signFileOutput.close();

				// Guarda la llave pública en un archivo
				byte[] key = pub.getEncoded();
				FileOutputStream keyFileOutput = new FileOutputStream("RSApk.txt");
				keyFileOutput.write(key);
				keyFileOutput.close();

				// Despliega tiempo
				double time = (fTime - iTime) / 1000000.0;
				System.out.println("Tiempo transcurrido: " + time + "ms");

			} catch (Exception e) {
				System.err.println("Caught exception " + e.toString());
			}
		}
	}
}