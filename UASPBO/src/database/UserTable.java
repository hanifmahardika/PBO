package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import transaksi.Users;

public class UserTable {
	private Connection koneksi;
	
	public UserTable(){
		try {
			Class.forName(Config.DATABASE_DRIVER).newInstance();
			koneksi=DriverManager.getConnection(Config.URL,Config.username,Config.password);
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insert(Users s){
		try{
			Statement stmt=koneksi.createStatement();
			stmt.executeUpdate("INSERT INTO Users VALUES('"+s.getId()+
											"','"+s.getNama()+"','"+s.getPass()+"','"+s.getJenis()+"')");
			stmt.close();
		}catch(SQLException x){
			x.printStackTrace();
		}
	}

	public void delete(String id){
		try{
			Statement stmt=koneksi.createStatement();
			stmt.executeUpdate("DELETE FROM Users WHERE id='"+id+"'");
			stmt.close();
		}catch(SQLException x){
			x.printStackTrace();
		}
	}

	public void update(String key, Users s){
		try{
			Statement stmt=koneksi.createStatement();
			stmt.executeUpdate("UPDATE Users SET nama='"+s.getNama()+"', pass='"+s.getPass()+"', jenis='"+s.getJenis()+"' WHERE id='"+key+"'");
			stmt.close();
		}catch(SQLException x){
			x.printStackTrace();
		}
	}
	
	public Vector<Users> viewAll(){
		Vector<Users> vHasil=new Vector<Users>();
		Users pHasil=null;
		try{
			Statement stmt=koneksi.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM Users");
			while(rs.next()){
				String id=rs.getString(1);
				String nama=rs.getString(2);
				String pass=rs.getString(3);
				String jenis=rs.getString(4);
				pHasil=new Users(id,nama,pass,jenis);
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
			ResultSet rs=stmt.executeQuery("SELECT * FROM Users");
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

	public Vector<Users> viewAll(String nama){
		Vector<Users> vHasil=new Vector<Users>();
		Users pHasil=null;
		try{
			Statement stmt=koneksi.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM Users WHERE Nama like '"+nama+"'");
			while(rs.next()){
				String id=rs.getString(1);
				String pass=rs.getString(3);
				String jenis=rs.getString(4);
				pHasil=new Users(id,nama,pass,jenis);
				vHasil.add(pHasil);
			}
			stmt.close();
		}catch(SQLException x){
			x.printStackTrace();
		}
		return vHasil;
	}

	public Users cariById(String id){
		Users pHasil=new Users("","","","");
		try{
			Statement stmt=koneksi.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM Users WHERE id='"+id+"'");
			while(rs.next()){
				String nama=rs.getString(2);
				String pass=rs.getString(3);
				String jenis=rs.getString(4);
				pHasil=new Users(id,nama,pass,jenis);
			}
			stmt.close();
		}catch(SQLException x){
			x.printStackTrace();
		}
		return pHasil;
	}

	public Users cariByNama(String nama){
		Users pHasil=null;
		Vector<Users> x= new Vector<Users>(viewAll());
		
		for(int i=0; i<x.size();i++){
			if(x.elementAt(i).getNama().equals(nama)){
				pHasil=x.elementAt(i);
				break;
			}
		}
		
		return pHasil;
	}
}
