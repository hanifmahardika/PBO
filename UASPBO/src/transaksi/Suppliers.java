package transaksi;

public class Suppliers {
	private String id, nama;
	public Suppliers(String id, String nama) {
		this.id = id;
		this.nama = nama;
	}

	public String getNama() {
		return nama;
	}
	
	public String getId() {
		return id;
	}
}
