package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import transaksi.Suppliers;

public class SupplierTable {
	private Connection koneksi;
	
	public SupplierTable(){
		try {
			Class.forName(Config.DATABASE_DRIVER).newInstance();
			koneksi=DriverManager.getConnection(Config.URL,Config.username,Config.password);
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insert(Suppliers s){
		try{
			Statement stmt=koneksi.createStatement();
			stmt.executeUpdate("INSERT INTO MYSUPPLIER VALUES('"+s.getId()+
											"','"+s.getNama()+"')");
			stmt.close();
		}catch(SQLException x){
			x.printStackTrace();
		}
	}

	public void delete(String id){
		try{
			Statement stmt=koneksi.createStatement();
			stmt.executeUpdate("DELETE FROM MYSUPPLIER WHERE id='"+id+"'");
			stmt.close();
		}catch(SQLException x){
			x.printStackTrace();
		}
	}

	public void update(String key, Suppliers s){
		try{
			Statement stmt=koneksi.createStatement();
			stmt.executeUpdate("UPDATE MYSUPPLIER SET nama='"+s.getNama()+"' WHERE id='"+key+"'");
			stmt.close();
		}catch(SQLException x){
			x.printStackTrace();
		}
	}
	
	public Vector<Suppliers> viewAll(){
		Vector<Suppliers> vHasil=new Vector<Suppliers>();
		Suppliers pHasil=null;
		try{
			Statement stmt=koneksi.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM MYSUPPLIER");
			while(rs.next()){
				String id=rs.getString(1);
				String nama=rs.getString(2);
				pHasil=new Suppliers(id,nama);
				vHasil.add(pHasil);
			}
			stmt.close();
		}catch(SQLException x){
			x.printStackTrace();
		}
		return vHasil;
	}
	
	public Vector<String> viewAllName(){
		Vector<String> vHasil=new Vector<String>();
		String pHasil=null;
		try{
			Statement stmt=koneksi.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM MYSUPPLIER");
			while(rs.next()){
				String nama=rs.getString(2);
				pHasil=nama;
				vHasil.add(pHasil);
			}
			stmt.close();
		}catch(SQLException x){
			x.printStackTrace();
		}
		return vHasil;
	}

	public Vector<Suppliers> viewAll(String nama){
		Vector<Suppliers> vHasil=new Vector<Suppliers>();
		Suppliers pHasil=null;
		try{
			Statement stmt=koneksi.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM MYSUPPLIER WHERE Nama like '"+nama+"'");
			while(rs.next()){
				String id=rs.getString(1);
				pHasil=new Suppliers(id,nama);
				vHasil.add(pHasil);
			}
			stmt.close();
		}catch(SQLException x){
			x.printStackTrace();
		}
		return vHasil;
	}

	public Suppliers cariById(String id){
		Suppliers pHasil=null;
		try{
			Statement stmt=koneksi.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM MYSUPPLIER WHERE id='"+id+"'");
			while(rs.next()){
				String nama=rs.getString(2);
				pHasil=new Suppliers(id,nama);
			}
			stmt.close();
		}catch(SQLException x){
			x.printStackTrace();
		}
		return pHasil;
	}

	public Suppliers cariByNama(String nama){
		Suppliers pHasil=null;
		Vector<Suppliers> x= new Vector<Suppliers>(viewAll());
		
		for(int i=0; i<x.size();i++){
			if(x.elementAt(i).getNama().equals(nama)){
				pHasil=x.elementAt(i);
				break;
			}
		}
		
		return pHasil;
	}
}
