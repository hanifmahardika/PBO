package transaksi;

public class DetilTransaksi {
	private String id;
	private int harga, jumlah;

	public DetilTransaksi(String id, int harga, int jumlah) {
		this.id = id;
		this.harga = harga;
		this.jumlah = jumlah;
	}

	public int getSubTotal() {
		return jumlah*harga;
	}

	public int getJumlah() {
		return jumlah;
	}
	
	public void setJumlah(int jumlah) {
		this.jumlah = jumlah;
	}
	
	public String getId() {
		return id;
	}
	
	public int getHarga() {
		return harga;
	}
}
