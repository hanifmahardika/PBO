package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import transaksi.DetilTransaksi;
import transaksi.Transaksi;

public class TransaksiTable {
	private Connection koneksi;
	
	public TransaksiTable(){
		try {
			Class.forName(Config.DATABASE_DRIVER).newInstance();
			koneksi=DriverManager.getConnection(Config.URL,Config.username,Config.password);
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insert(Transaksi t){
		try{
			Statement stmt=koneksi.createStatement();
			stmt.executeUpdate("INSERT INTO Transaksi VALUES('"+t.getId()+
											"','"+t.getTgl()+"','"+t.getWkt()+"','"+t.getUser().getId()+"')");
			for(int i=0;i<t.getdTransaksi().size();i++){
				stmt.executeUpdate("INSERT INTO DetilTransaksi VALUES('"+t.getdTransaksi().elementAt(i).getId()+
												"','"+t.getId()+"',"+t.getdTransaksi().elementAt(i).getHarga()+
												","+t.getdTransaksi().elementAt(i).getJumlah()+")");
				stmt.executeUpdate("UPDATE Products SET stok=(stok-"+t.getdTransaksi().elementAt(i).getJumlah()+") "
									+ "WHERE id='"+t.getdTransaksi().elementAt(i).getId()+"'");
			}
			stmt.close();
		}catch(SQLException x){
			x.printStackTrace();
		}
	}

	public void delete(String id){
		try{
			Statement stmt=koneksi.createStatement();
			stmt.executeUpdate("DELETE FROM DetilTransaksi WHERE id_trx='"+id+"'");
			stmt.executeUpdate("DELETE FROM Transaksi WHERE id_trx='"+id+"'");
			stmt.close();
		}catch(SQLException x){
			x.printStackTrace();
		}
	}

	public void update(String key, DetilTransaksi dt){
		try{
			Statement stmt=koneksi.createStatement();
			stmt.executeUpdate("UPDATE DetilTransaksi SET jumlah='"+dt.getJumlah()+"' WHERE id_trx='"+key+"'");
			stmt.close();
		}catch(SQLException x){
			x.printStackTrace();
		}
	}
	
	public Vector<Transaksi> viewAll(){
		Vector<Transaksi> vHasil=new Vector<Transaksi>();
		Transaksi pHasil=null;
		try{
			Statement stmt=koneksi.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM Transaksi");
			while(rs.next()){
				String id=rs.getString(1);
				String tgl=rs.getString(2);
				String wkt=rs.getString(3);
				String idU=rs.getString(4);
				pHasil=new Transaksi(id,tgl,wkt,idU);
				Statement stmt2=koneksi.createStatement();
				ResultSet rs2=stmt2.executeQuery("SELECT * FROM DetilTransaksi where id_trx='"+id+"'");
				while(rs2.next()){
					String id_brg=rs2.getString(1);
					int harga=rs2.getInt(3);
					int jumlah=rs2.getInt(4);
					pHasil.tambahdTransaksi(new DetilTransaksi(id_brg, harga, jumlah));
				}
				vHasil.add(pHasil);
			}
			stmt.close();
		}catch(SQLException x){
			x.printStackTrace();
		}
		return vHasil;
	}
	
	public Vector<Transaksi> cariByTanggal(String tgl){
		Vector<Transaksi> vHasil=new Vector<Transaksi>();
		Transaksi pHasil=null;
		try{
			Statement stmt=koneksi.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM Transaksi where tanggal=dateob('"+tgl+"')");
			while(rs.next()){
				String id=rs.getString(1);
				String wkt=rs.getString(3);
				String idU=rs.getString(4);
				pHasil=new Transaksi(id,tgl,wkt,idU);
				Statement stmt2=koneksi.createStatement();
				ResultSet rs2=stmt2.executeQuery("SELECT * FROM DetilTransaksi where id_trx='"+id+"'");
				while(rs2.next()){
					String id_brg=rs2.getString(1);
					int harga=rs2.getInt(3);
					int jumlah=rs2.getInt(4);
					pHasil.tambahdTransaksi(new DetilTransaksi(id_brg, harga, jumlah));
				}
				vHasil.add(pHasil);
			}
			stmt.close();
		}catch(SQLException x){
			x.printStackTrace();
		}
		return vHasil;
	}

	public Transaksi cariById(String id){
		Transaksi pHasil=null;
		try{
			Statement stmt=koneksi.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM Transaksi where id_trx='"+id+"'");
			while(rs.next()){
				String tgl=rs.getString(2);
				String wkt=rs.getString(3);
				String idU=rs.getString(4);
				pHasil=new Transaksi(id,tgl,wkt,idU);
				Statement stmt2=koneksi.createStatement();
				ResultSet rs2=stmt2.executeQuery("SELECT * FROM DetilTransaksi where id_trx='"+id+"'");
				while(rs2.next()){
					String id_brg=rs2.getString(1);
					int harga=rs2.getInt(3);
					int jumlah=rs2.getInt(4);
					pHasil.tambahdTransaksi(new DetilTransaksi(id_brg, harga, jumlah));
				}
			}
			stmt.close();
		}catch(SQLException x){
			x.printStackTrace();
		}
		return pHasil;
	}

	public Vector<Transaksi> cariByIdU(String idU){
		Vector<Transaksi> vHasil=new Vector<Transaksi>();
		Transaksi pHasil=null;
		try{
			Statement stmt=koneksi.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM Transaksi where id_user='"+idU+"'");
			while(rs.next()){
				String id=rs.getString(1);
				String tgl=rs.getString(2);
				String wkt=rs.getString(3);
				pHasil=new Transaksi(id,tgl,wkt,idU);
				Statement stmt2=koneksi.createStatement();
				ResultSet rs2=stmt2.executeQuery("SELECT * FROM DetilTransaksi where id_trx='"+id+"'");
				while(rs2.next()){
					String id_brg=rs2.getString(1);
					int harga=rs2.getInt(3);
					int jumlah=rs2.getInt(4);
					pHasil.tambahdTransaksi(new DetilTransaksi(id_brg, harga, jumlah));
				}
				vHasil.add(pHasil);
			}
			stmt.close();
		}catch(SQLException x){
			x.printStackTrace();
		}
		return vHasil;
	}

	public Vector<Transaksi> cariByIdB(String idB){
		Vector<Transaksi> vHasil=new Vector<Transaksi>();
		Transaksi pHasil=null;
		try{
			Statement stmt=koneksi.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM Transaksi");
			while(rs.next()){
				String id=rs.getString(1);
				String tgl=rs.getString(2);
				String wkt=rs.getString(3);
				String idU=rs.getString(4);
				pHasil=new Transaksi(id,tgl,wkt,idU);
				Statement stmt2=koneksi.createStatement();
				ResultSet rs2=stmt2.executeQuery("SELECT id_barang,harga,jumlah,count(id_barang) FROM DetilTransaksi where id_trx='"+id+"' "
									+ "and id_barang='"+idB+"' group by id_barang,harga,jumlah");
				int ada=0;
				while(rs2.next()){
					String id_brg=rs2.getString(1);
					int harga=rs2.getInt(2);
					int jumlah=rs2.getInt(3);
					ada=rs2.getInt(4);
					pHasil.tambahdTransaksi(new DetilTransaksi(id_brg, harga, jumlah));
				}
				if(ada!=0){
					vHasil.add(pHasil);
				}
			}
			stmt.close();
		}catch(SQLException x){
			x.printStackTrace();
		}
		return vHasil;
	}

	public String nextId(String thn){
		String pHasil=null;
		try{
			Statement stmt=koneksi.createStatement();
			ResultSet rs=stmt.executeQuery("select substring(concat('000',tonumber(substring(max(id_trx),4,3))+1),"
										+ "length(concat('000',tonumber(substring(max(id_trx),4,3))+1))-2,3) "
										+ "from Transaksi where substring(id_trx,1,3)='I"+thn+"'");
			while(rs.next()){
				pHasil="I" + thn + rs.getString(1);
			}
			stmt.close();
		}catch(SQLException x){
			x.printStackTrace();
		}

		if(pHasil.equals("I" + thn +"null")){
			pHasil="I" + thn + "001";
		}

		return pHasil;
	}
}
