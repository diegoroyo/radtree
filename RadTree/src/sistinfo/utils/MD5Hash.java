package sistinfo.utils;

import java.security.MessageDigest;

public class MD5Hash {

	public static String getMD5Hash(String string) {
		try {
        	byte[] claveBytes = string.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("MD5");
			return new String(md.digest(claveBytes));
		} catch (Exception e) {
			System.err.println("RegistroServlet: Error al obtener el hash de la contraseña de usuario");
		}
		return null;
	}
	
}
