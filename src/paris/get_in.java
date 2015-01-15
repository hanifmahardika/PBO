package paris;


import java.io.*;
import java.net.*;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class get_in 
{
	private static InetAddress host;
	private static final int PORT = 3456;
	public static get_in tgl = new get_in();
	//public static final String ip = "get_in";
	
	private String getTanggalmasuk()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public static void main(String[] args) 
	{
		try 
		{
			host = InetAddress.getByName("");
			//host = InetAddress.getLocalHost();
		}catch (UnknownHostException uhEx) 
		{
			System.out.println("Host ID tidak ditemukan!");
			System.exit(1);
		}
		accessServer();
	}

	public static void accessServer()
	{
		Socket link = null;
		//Step 1.
		try
		{
			link = new Socket(host,PORT);
			//Step 1.membuka input stream
			Scanner input = new Scanner(link.getInputStream());
			//Step 2.membuka output stream
			PrintWriter output = new PrintWriter(link.getOutputStream(),true);
			//Step 2.
			//Set up stream untuk masukan dari keyboard
			Scanner userEntry = new Scanner(System.in);
			String message, response;
			String n_pol, masuk;
			do
			{
				System.out.print("Masukan No Polisi : ");
				n_pol = userEntry.nextLine();
				masuk = tgl.getTanggalmasuk();
				
				
				output.println(n_pol);
				output.println(masuk);
				
				System.out.print("Tekan 'No' (untuk keluar) : ");
				message = userEntry.nextLine();
				// Step 3.
				
				response = input.nextLine(); //Step 3.
				//System.out.println("\n"+new Date() +"SERVER> "+response);
				System.out.println(response);
			}while (!message.equalsIgnoreCase("No"));
		}
		catch(IOException ioEx)
		{
			ioEx.printStackTrace();
		}
		finally
		{
			try
			{
				System.out.println(
						"\n* Closing connection... *");
				link.close();
				//Step 4.
			}
			catch(IOException ioEx)
			{
				System.out.println(
						"Unable to disconnect!");
				System.exit(1);
			}
		}
	}
}