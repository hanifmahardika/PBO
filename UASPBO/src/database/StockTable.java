package database;

import java.util.*;
import java.sql.*;

import transaksi.*;

public class StockTable{
	private Connection koneksi;
	
	public StockTable(){
		try {
			Class.forName(Config.DATABASE_DRIVER).newInstance();
			koneksi=DriverManager.getConnection(Config.URL,Config.username,Config.password);
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insert(Stocks p){
		try{
			Statement stmt=koneksi.createStatement();
			stmt.executeUpdate("INSERT INTO Stock VALUES('"+p.getProduct().getId()+
											"','"+p.getUser().getId()+"','"+p.getTanggal()+
											"','"+p.getWaktu()+"','"+p.getJumlah()+"','"+p.getStok()+"')");
			stmt.executeUpdate("UPDATE Products SET stock="+p.getStok()+ "WHERE id='"+p.getProduct().getId()+"'");
			stmt.close();
		}catch(SQLException x){
			x.printStackTrace();
		}
	}
	
	public Vector<Stocks> viewAll(){
		Vector<Stocks> vHasil=new Vector<Stocks>();
		Stocks pHasil=null;
		try{
			Statement stmt=koneksi.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM Stock");
			while(rs.next()){
				String idB=rs.getString(1);
				String idU=rs.getString(2);
				String tanggal=rs.getString(3);
				String waktu=rs.getString(4);
				int jumlah=rs.getInt(5);
				int stok=rs.getInt(6);
				pHasil=new Stocks(idB,idU,tanggal,waktu,jumlah,stok);
				vHasil.add(pHasil);
			}
			stmt.close();
		}catch(SQLException x){
			x.printStackTrace();
		}
		return vHasil;
	}
	
	public Vector<Stocks> cariByIdB(String idB){
		Vector<Stocks> vHasil=new Vector<Stocks>();
		Stocks pHasil=null;
		try{
			Statement stmt=koneksi.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM Stock WHERE id_barang='"+idB+"'");
			while(rs.next()){
				String idU=rs.getString(2);
				String tanggal=rs.getString(3);
				String waktu=rs.getString(4);
				int jumlah=rs.getInt(5);
				int stok=rs.getInt(6);
				pHasil=new Stocks(idB,idU,tanggal,waktu,jumlah,stok);
				vHasil.add(pHasil);
			}
			stmt.close();
		}catch(SQLException x){
			x.printStackTrace();
		}
		return vHasil;
	}

	public Vector<Stocks> cariByIdU(String idU){
		Vector<Stocks> vHasil=new Vector<Stocks>();
		Stocks pHasil=null;
		try{
			Statement stmt=koneksi.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM Stock WHERE id_user='"+idU+"'");
			while(rs.next()){
				String idB=rs.getString(1);
				String tanggal=rs.getString(3);
				String waktu=rs.getString(4);
				int jumlah=rs.getInt(5);
				int stok=rs.getInt(6);
				pHasil=new Stocks(idB,idU,tanggal,waktu,jumlah,stok);
				vHasil.add(pHasil);
			}
			stmt.close();
		}catch(SQLException x){
			x.printStackTrace();
		}
		return vHasil;
	}

	public Vector<Stocks> cariByIdTanggal(String tanggal){
		Vector<Stocks> vHasil=new Vector<Stocks>();
		Stocks pHasil=null;
		try{
			Statement stmt=koneksi.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM Stock WHERE tanggal='"+tanggal+"'");
			while(rs.next()){
				String idB=rs.getString(1);
				String idU=rs.getString(2);
				String waktu=rs.getString(4);
				int jumlah=rs.getInt(5);
				int stok=rs.getInt(6);
				pHasil=new Stocks(idB,idU,tanggal,waktu,jumlah,stok);
				vHasil.add(pHasil);
			}
			stmt.close();
		}catch(SQLException x){
			x.printStackTrace();
		}
		return vHasil;
	}

	public int getStock(String idB){
		int hasil=0;
		Stocks x=getStockDetil(idB);
		if(x!=null){
			hasil=x.getStok();
		}
		return hasil;
	}

	public Stocks getStockDetil(String idB){
		Stocks pHasil=null;
		try{
			Statement stmt=koneksi.createStatement();
			String maxTgl="(select max(tanggal) FROM Stock WHERE id_barang='"+idB+"')";
			String maxWkt="(select max(waktu) FROM Stock WHERE id_barang='"+idB+"' and tanggal="+maxTgl+")";
			ResultSet rs=stmt.executeQuery("SELECT * FROM Stock WHERE id_barang='"+idB+"' and "
										+ "tanggal="+maxTgl+" and waktu="+maxWkt);
			while(rs.next()){
				String idU=rs.getString(2);
				String tanggal=rs.getString(3);
				String waktu=rs.getString(4);
				int jumlah=rs.getInt(5);
				int stok=rs.getInt(6);
				pHasil=new Stocks(idB,idU,tanggal,waktu,jumlah,stok);
			}
			stmt.close();
		}catch(SQLException x){
			x.printStackTrace();
		}
		return pHasil;
	}
}
