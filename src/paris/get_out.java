package paris;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class get_out
{
	private static InetAddress host;
	private static final int PORT = 3456;
	public static Vector jam_keluar = new Vector();
	public static get_out tgll = new get_out();
	public static int data_ke;
	//public static final String ip = "get_out";
	
	private String getTanggal()
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
			String message = "tidak";
			String response;
			String n_pol, keluar;
			do
			{
				System.out.print("Masukan No Polisi : ");
				n_pol = userEntry.nextLine();
				keluar = tgll.getTanggal();
				
				for(int a = 0; a < server.no_pol.size(); a++)
				{
					if((server.no_pol.elementAt(a).toString()).equalsIgnoreCase(n_pol))
					{
						message = "ada";
						server.keluar.add(keluar);
						data_ke = a;
						break;
					}
				}
				if(message == "ada")
				{
					output.println(server.no_pol.elementAt(data_ke));
					output.println(server.masuk.elementAt(data_ke));
					output.println(server.keluar.elementAt(data_ke));
					
					System.out.print("Tekan 'Ya' (untuk lanjutkan transaksi) : ");
					message = userEntry.nextLine();
					
					server.no_pol.removeElementAt(data_ke);
					server.masuk.removeElementAt(data_ke);
					server.keluar.removeElementAt(data_ke);
				}
				else
				{
					System.out.println("Maaf No Polisi Belum Parkir (Tidak Ada)");
				}
				// Step 3.
				response = input.nextLine(); //Step 3.
				System.out.println("\n"+new Date() +"SERVER> "+response);
			}while (message.equalsIgnoreCase("Ya"));
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