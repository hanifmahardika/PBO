package aa;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ServerParkirSocket {
	private static ServerSocket serverSocket;
	public static LogKendaraan lKendaraan;
	private static final int PORT = 8080;
	public static void main(String[] args)
			throws IOException
			{
		lKendaraan=new LogKendaraan();
		try
		{
			serverSocket = new ServerSocket(PORT);
			System.out.println("SERVER PARKIR PADA PORT:" + PORT);
		}
		catch (IOException ioEx)
		{
			System.out.println("\nTidak Dapat Membuka Port!");
			System.exit(1);
		}
		do
		{
			System.out.println("Server Siap...");
			Socket client = serverSocket.accept();
			System.out.println("\nClient Baru ..masuk.." + client.getRemoteSocketAddress().toString());
			ClientHandler handler =
					new ClientHandler(client);
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
		client = socket;
		try
		{
			input = new Scanner(client.getInputStream());
			output = new PrintWriter(
					client.getOutputStream(),true);
			output.println("Selamat Datang di Server Parkir");
		}
		catch(IOException ioEx){
			ioEx.printStackTrace();
		}
	}
	public void run()
	{
		String received="";
		do
		{

			try{
				received = input.nextLine();
				System.out.println(received);
				String []data=received.split(";");
				if(data!=null){
					if(data[1].equalsIgnoreCase("IN"))
						ServerParkirSocket.lKendaraan.masuk(data[2]);
				}
				output.println("Data: " + ServerParkirSocket.lKendaraan.getInfo());
			}catch(java.util.NoSuchElementException noex){}

			//ulangi sampai QUIT
		}while (!received.equalsIgnoreCase("QUIT"));
		try
		{
			if (client!=null)
			{
				System.out.println(
						"Menutup koneksi...sukses..");
				client.close();
			}
			else{
				System.out.println(
						"Menutup koneksi...sukses..");
			}
		}
		catch(IOException ioEx)
		{
			System.out.println("Gagal menutup koneksi!");
		}
	}

}
