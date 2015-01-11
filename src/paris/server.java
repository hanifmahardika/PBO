package paris;

import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class server
{
	public static Vector no_pol = new Vector();
	public static Vector masuk = new Vector();
	public static Vector keluar = new Vector();
	
	private static ServerSocket serverSocket;
	private static final int PORT = 3456;
	public static void main(String[] args)throws IOException
	{
		try
		{
			serverSocket = new ServerSocket(PORT);
			System.out.println("Server Parkir Pada PORT:" + PORT);
		}
		catch (IOException ioEx)
		{
			System.out.println("\nTidak Dapat Membuka Port!");
			System.exit(1);
		}
		do
		{
			//Tunggu Koneksi
			System.out.println("Server Siap...");
			Socket client = serverSocket.accept();
			
			System.out.println("\nClient Baru ..masuk..dengan IP: "+client.getInetAddress().toString()+"\n");
			//Create a thread to handle communication with
			//this client and pass the constructor for this
			//thread a reference to the relevant socket...
			ClientHandler handler =	new ClientHandler(client);
			handler.start();//As usual, method calls run .
		}while (true);
	}
}

class ClientHandler extends Thread
{
	private Socket client;
	private Scanner input;
	private PrintWriter output;
	
	public ClientHandler(Socket socket)
	{
		//Set up reference to associated socket...
		client = socket;
		try
		{
			input = new Scanner(client.getInputStream());
			output = new PrintWriter(client.getOutputStream(),true);
			output.println("Selamat Datang Di Server Parkir . . .");
		}
		catch(IOException ioEx){
			ioEx.printStackTrace();
		}
	}
	
	private String getTanggalkeluar()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public void run()
	{
		String received,masuk,keluar,message;
		do
		{
			//menerima pesan dari client
			//melalui socket input stream..
			received = input.nextLine();
			masuk = input.nextLine();
		
			//Menampilkan kembali pesan ke client
			//melalui socket output stream...
			if(client.getInetAddress().getHostName().toString().equalsIgnoreCase("172.16.3.239"))
			{
				System.out.println("Pesan Masuk Dari : " + client.getInetAddress().getHostName().toString());
				System.out.println("Dengan IP 	:" + client.getInetAddress().toString());
				System.out.println("No Polisi	: " + received);
				System.out.println("Jam Masuk 	: " + masuk);
			}
			else
			{
				//keluar = input.nextLine();
				keluar = getTanggalkeluar();
				System.out.println("sudah keluar");
				System.out.println("Pesan Masuk Dari : " + client.getInetAddress().getHostName().toString());
				System.out.println("Dengan IP 	: " + client.getInetAddress().toString());
				System.out.println("No Polisi	: " + received);
				System.out.println("Jam Masuk	: " + masuk);
				System.out.println("Jam keluar 	: " + keluar);
				
			}
			message = input.nextLine();
			//ulangi sampai QUIT
		}while (!message.equalsIgnoreCase("No"));
		try
		{
			if (client!=null)
			{
				System.out.println(
						"Menutup koneksi...sukses..");
				client.close();
			}
		}
		catch(IOException ioEx)
		{
			System.out.println("Gagal menutup koneksi!");
		}
	}
}