package transaksi;

import database.*;

public class Stocks {
	private Users user;
	private Products product;
	private String tanggal, waktu;
	private int stok, jumlah;

	public Stocks(String idProduk, String idUser, String tanggal, String waktu, int jumlah, int stok) {
		ProductTable pt=new ProductTable();
		UserTable ut=new UserTable();

		product=pt.cariById(idProduk);
		user=ut.cariById(idUser);
		
		this.tanggal=tanggal;
		this.waktu=waktu;
		this.jumlah=jumlah;
		this.stok=stok;
	}

	public Users getUser() {
		return user;
	}

	public Products getProduct() {
		return product;
	}

	public String getTanggal() {
		return tanggal;
	}

	public String getWaktu() {
		return waktu;
	}

	public int getStok() {
		return stok;
	}

	public int getJumlah() {
		return jumlah;
	}
}
