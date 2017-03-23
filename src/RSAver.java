import java.io.*;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;

public class RSAver {

	public static void main(String[] args) {
		if (args.length != 3) {
			// No tiene los argumentos correctos para el programa
			System.out.println("Uso: RSAsign <llavePublica> <firma> <archivoParaVerificar>");
		} else {
			try {
				// Guarda tiempo inicial
				double iTime = System.nanoTime();

				// Lee el archivo de la llave publica
				FileInputStream keyFileInput = new FileInputStream(args[0]);
		        byte[] encodedKey = new byte[keyFileInput.available()];
		        keyFileInput.read(encodedKey);
		        keyFileInput.close();
		        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encodedKey);

		        // Genera llave con la llave publica dada
		        KeyFactory keyFactory = KeyFactory.getInstance("RSA", "SUN");
	        	PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);

		        // Lee el archivo de la firma
		        FileInputStream signFileInput = new FileInputStream(args[1]);
		        byte[] signToVerify = new byte[signFileInput.available()];
		        signFileInput.read(signToVerify);
		        signFileInput.close();

		        // Inicializa firma con la llave publica
		        Signature sign = Signature.getInstance("SHA1withDSA", "SUN");
	        	sign.initVerify(pubKey);

	        	// Verifica la firma al leer el archivo
	        	FileInputStream fileInput = new FileInputStream(args[2]);
		        BufferedInputStream bufferIn = new BufferedInputStream(fileInput);
		        byte[] buffer = new byte[1024];
		        int len;
		        while (fileInput.available() != 0) {
		          len = fileInput.read(buffer);
		          sign.update(buffer, 0, len);
		        }
		        fileInput.close();

		        // Guarda tiempo final
				double fTime = System.nanoTime();

		        // Revisa si firma coincide con lo firmado
		        boolean verifies = sign.verify(signToVerify);
	        	System.out.println("Archivo fidedigno: " + verifies);

	        	// Despliega tiempo
				double time = (fTime - iTime) / 1000000.0;
				System.out.println("Tiempo transcurrido: " + time + "ms");

			} catch (Exception e) {
        		System.err.println("Caught exception " + e.toString());
			}
		}
	}
}