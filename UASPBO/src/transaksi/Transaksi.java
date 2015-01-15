package transaksi;

import java.util.Vector;

import database.UserTable;

public class Transaksi {
	private Vector<DetilTransaksi> dTransaksi=new Vector<DetilTransaksi>();
	private Users user;
	private String id, tgl, wkt;

	public int getTotal(){
		int total=0;
		for(int i=0;i<dTransaksi.size();i++){
			total+=dTransaksi.elementAt(i).getSubTotal();
		}
		return total;
	}
	
	public int getQtyProductByID(String idB){
		int jumlah=0;
		for(int i=0;i<dTransaksi.size();i++){
			if(dTransaksi.elementAt(i).getId().equals(idB)){
				jumlah=dTransaksi.elementAt(i).getJumlah();
				break;
			}
		}
		return jumlah;
	}
	
	public Transaksi(String id, String tgl, String wkt, String idU) {
		// TODO Auto-generated constructor stub
		this.id=id;
		this.tgl=tgl;
		this.wkt=wkt;
		UserTable ut=new UserTable();
		user=ut.cariById(idU);
	}
	
	public Transaksi(String id, String tgl, String wkt,String idU, Vector<DetilTransaksi> dTransaksi) {
		// TODO Auto-generated constructor stub
		this.id=id;
		this.tgl=tgl;
		this.wkt=wkt;
		UserTable ut=new UserTable();
		user=ut.cariById(idU);
		this.dTransaksi=dTransaksi;
	}

	public Vector<DetilTransaksi> getdTransaksi() {
		return dTransaksi;
	}
	
	public boolean tambahdTransaksi(DetilTransaksi brg){
		boolean ada=false;
		
		for(int i=0; i<dTransaksi.size(); i++){
			if(dTransaksi.elementAt(i).getId().equals(brg.getId())){
				ada=true;
				break;
			}
		}
		
		if(!ada){
			dTransaksi.add(brg);
		}
		return !ada;
	}

	public Users getUser() {
		return user;
	}
	
	public String getTgl() {
		return tgl;
	}
	
	public String getWkt() {
		return wkt;
	}
	
	public String getId() {
		return id;
	}
}
