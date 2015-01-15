package transaksi;

import java.security.*;

public class Users {
	private String id, nama, pass, jenis;
	public Users(String id, String nama, String pass, String jenis) {
		this.id = id;
		this.nama = nama;
		this.pass = pass;
		this.jenis = jenis;
	}

	public String getNama() {
		return nama;
	}
	
	public String getId() {
		return id;
	}

	public String getPass() {
		return pass;
	}
	
	public String getJenis() {
		return jenis;
	}

	//public static String generateMD5(String s){
        //StringBuffer sb = new StringBuffer();
		//MessageDigest md;
		//try {
			//md = MessageDigest.getInstance("MD5");
			//md.update(s.getBytes());
			//byte byteData[] = md.digest();
			 
	        //convert the byte to hex format method 1
	       // for (int i = 0; i < byteData.length; i++) {
	      //   sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	      //  }
	//	} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
		//return new String(sb.toString());
	//}
}
