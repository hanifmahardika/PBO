package transaksi;

public class Products {
	private String id, nama, idSup;
	private int harga, stok;
	public Products(String id, String nama, String idSup, int harga, int stok) {
		this.id = id;
		this.nama = nama;
		this.idSup = idSup;
		this.harga = harga;
		this.stok = stok;
	}

	public String getNama() {
		return nama;
	}
	
	public String getIdSup() {
		return idSup;
	}
	
	public int getHarga() {
		return harga;
	}
	
	public int getStok() {
		return stok;
	}
	
	public String getId() {
		return id;
	}
}
