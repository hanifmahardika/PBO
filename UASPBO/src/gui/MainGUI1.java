package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

public class MainGUI1 extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGUI1 frame = new MainGUI1();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainGUI1() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmLogout = new JMenuItem("logout");
		mnNewMenu.add(mntmLogout);
		
		JMenu mnNewMenu_1 = new JMenu("Menu");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmMaintenanceProduk = new JMenuItem("maintenance produk");
		mnNewMenu_1.add(mntmMaintenanceProduk);
		
		JMenuItem mntmMaintenanceUser = new JMenuItem("maintenance user");
		mnNewMenu_1.add(mntmMaintenanceUser);
		
		JMenuItem mntmMaintenanceStock = new JMenuItem("maintenance stock");
		mnNewMenu_1.add(mntmMaintenanceStock);
		
		JMenu mnPenjualan = new JMenu("Penjualan");
		menuBar.add(mnPenjualan);
		
		JMenuItem mntmPointOfSale = new JMenuItem("point of sale");
		mnPenjualan.add(mntmPointOfSale);
		
		JMenu mnLaporan = new JMenu("laporan");
		menuBar.add(mnLaporan);
		
		JMenuItem mntmViewReport = new JMenuItem("view report");
		mnLaporan.add(mntmViewReport);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
