package database;

import java.util.*;
import java.sql.*;

import transaksi.*;

public class ProductTable{
	private Connection koneksi;
	
	public ProductTable(){
		try {
			Class.forName(Config.DATABASE_DRIVER).newInstance();
			koneksi=DriverManager.getConnection(Config.URL,Config.username,Config.password);
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insert(Products p){
		try{
			Statement stmt=koneksi.createStatement();
			stmt.executeUpdate("INSERT INTO Products VALUES('"+p.getId()+
											"','"+p.getNama()+"','"+p.getIdSup()+
											"','"+p.getHarga()+"','"+p.getStok()+"')");
			
			stmt.close();
		}catch(SQLException x){
			x.printStackTrace();
		}
	}

	public void delete(String id){
		try{
			Statement stmt=koneksi.createStatement();
			stmt.executeUpdate("DELETE FROM Products WHERE id='"+id+"'");
			stmt.close();
		}catch(SQLException x){
			x.printStackTrace();
		}
	}

	public void update(String key, Products p){
		try{
			Statement stmt=koneksi.createStatement();
			stmt.executeUpdate("UPDATE Products SET nama='"+p.getNama()+"', idSupp='"+p.getIdSup()+
											"', harga='"+p.getHarga()+"' WHERE id='"+key+"'");
			stmt.close();
		}catch(SQLException x){
			x.printStackTrace();
		}
	}
	
	public Vector<Products> viewAll(){
		Vector<Products> vHasil=new Vector<Products>();
		Products pHasil=null;
		try{
			Statement stmt=koneksi.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM Products");
			while(rs.next()){
				String id=rs.getString(1);
				String nama=rs.getString(2);
				String idSup=rs.getString(3);
				int harga=rs.getInt(4);
				int stok=rs.getInt(5);
				pHasil=new Products(id,nama,idSup,harga,stok);
				vHasil.add(pHasil);
			}
			stmt.close();
		}catch(SQLException x){
			x.printStackTrace();
		}
		return vHasil;
	}
	
	public Vector<Products> viewAll(String nama){
		Vector<Products> vHasil=new Vector<Products>();
		Products pHasil=null;
		try{
			Statement stmt=koneksi.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM Products WHERE nama like '"+nama+"'");
			while(rs.next()){
				String id=rs.getString(1);
				String idSup=rs.getString(3);
				int harga=rs.getInt(4);
				int stok=rs.getInt(5);
				pHasil=new Products(id,nama,idSup,harga,stok);
				vHasil.add(pHasil);
			}
			stmt.close();
		}catch(SQLException x){
			x.printStackTrace();
		}
		return vHasil;
	}

	public Products cariById(String id){
		Products pHasil=null;
		try{
			Statement stmt=koneksi.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM Products WHERE id='"+id+"'");
			while(rs.next()){
				String nama=rs.getString(2);
				String idSup=rs.getString(3);
				int harga=rs.getInt(4);
				int stok=rs.getInt(5);
				pHasil=new Products(id,nama,idSup,harga,stok);
			}
			stmt.close();
		}catch(SQLException x){
			x.printStackTrace();
		}
		return pHasil;
	}

	public Vector<Products> cariBySupp(String nama){
		SupplierTable st=new SupplierTable();

		Vector<Products> pHasil=new Vector<Products>();
		Vector<Products> x= new Vector<Products>(viewAll());
		
		for(int i=0; i<x.size();i++){
			if(st.cariById(x.elementAt(i).getIdSup()).getNama().equals(nama)){
				pHasil.add(x.elementAt(i));
			}
		}
		
		return pHasil;
	}

	public Products cariByNama(String nama){
		Products pHasil=null;
		Vector<Products> x= new Vector<Products>(viewAll());
		
		for(int i=0; i<x.size();i++){
			if(x.elementAt(i).getNama().equals(nama)){
				pHasil=x.elementAt(i);
				break;
			}
		}
		
		return pHasil;
	}

	public String nextId(String idSup){
		String pHasil=null;
		try{
			Statement stmt=koneksi.createStatement();
			ResultSet rs=stmt.executeQuery("select substring(concat('000',tonumber(substring(max(id),4,3))+1),"
										+ "length(concat('000',tonumber(substring(max(id),4,3))+1))-2,3) "
										+ "from products where idSup='"+idSup+"'");
			while(rs.next()){
				pHasil=idSup + rs.getString(1);
			}
			stmt.close();
		}catch(SQLException x){
			x.printStackTrace();
		}

		if(pHasil.equals(idSup+"null")){
			pHasil=idSup + "001";
		}

		return pHasil;
	}
}
